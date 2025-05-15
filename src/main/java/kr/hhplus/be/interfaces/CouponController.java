package kr.hhplus.be.interfaces;


import kr.hhplus.be.CouponRedisService;
import kr.hhplus.be.domain.coupon.CouponService;
import kr.hhplus.be.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.domain.coupon.enumtype.CouponType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {
    private final CouponService couponService;
    private final CouponRedisService couponRedisService;

    public CouponController(CouponService couponService, CouponRedisService couponRedisService) {
        this.couponService = couponService;
        this.couponRedisService = couponRedisService;
    }

    // 쿠폰 발행(시스템에)
    @PostMapping("/publish")
    public void publishCoupon(@RequestBody CouponType type, @RequestBody BigDecimal discountAmount, @RequestBody Long amount) {
        couponService.publish(type, discountAmount, amount);
    }

    // 유저에게 쿠폰 발급
    @PostMapping("/issue/{couponId}")
    public UserCoupon issueCoupon(@PathVariable Long couponId, @RequestBody Long userId) {
        return couponService.issue(couponId, userId);
    }

    // 유저가 쿠폰 사용
    @PostMapping("/use/{userId}")
    public UserCoupon useCoupon(@PathVariable Long userId, @RequestBody Long couponId) {
       return couponService.use(userId, couponId);
    }

    @PostMapping("/redis/coupon/publish")
    public void publishCouponRedis(@RequestBody CouponType type, @RequestBody Long amount) {
        couponRedisService.publishCouponRedis(type, amount);
    }

    @PostMapping("/redis/coupon/requestIssue")
    public boolean requestIssueCoupon(@RequestBody Long couponId, @RequestBody Long userId) {
        return couponRedisService.requestIssueCoupon(couponId, userId);
    }
}
