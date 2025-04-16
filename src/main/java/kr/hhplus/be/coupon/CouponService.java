package kr.hhplus.be.coupon;

import kr.hhplus.be.coupon.entity.Coupon;
import kr.hhplus.be.coupon.entity.CouponIssue;
import kr.hhplus.be.coupon.enumtype.CouponType;
import kr.hhplus.be.coupon.repository.CouponIssueRepository;
import kr.hhplus.be.coupon.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponIssueRepository couponIssueRepository;

    public CouponService(CouponRepository couponRepository, CouponIssueRepository couponIssueRepository) {
        this.couponRepository = couponRepository;
        this.couponIssueRepository = couponIssueRepository;
    }

    public void publish(CouponType type, Long amount) {
        // 쿠폰 발행 로직
        Coupon coupon = new Coupon(type, amount);
        couponRepository.save(coupon);
    }

    public CouponIssue issue(Long couponId, Long userId) {
        // 쿠폰 발급 로직
        Coupon coupon = couponRepository.findById(couponId);
        if (coupon == null) {
            throw new RuntimeException("Coupon not found");
        }
        if (!coupon.canIssue()) {
            throw new RuntimeException("Coupon is not available for issue");
        }

        // 쿠폰 발급 처리
        return new CouponIssue(couponId, userId); // 쿠폰 발급 성공
    }

    public boolean use(Long userId, Long couponId) {
        // 쿠폰 사용 로직
        CouponIssue couponIssue = couponIssueRepository.findById(userId, couponId);
        if (couponIssue == null) {
            return false; // 쿠폰이 존재하지 않음
        }

        if (!couponIssue.canUse()) { // true 면 사용 가능, false 면 사용 불가
            return false; // 이미 사용된 쿠폰
        }
        couponIssue.use(); // 쿠폰 사용 처리
        return true; // 쿠폰 사용 성공
    }

}
