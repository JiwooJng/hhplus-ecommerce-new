package kr.hhplus.be.infrastructure.coupon;

import jakarta.persistence.NoResultException;
import kr.hhplus.be.domain.coupon.entity.Coupon;
import kr.hhplus.be.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.domain.coupon.repository.CouponRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CouponRepositoryImpl implements CouponRepository {
    private final CouponJpaRepository couponJpaRepository;
    private final UserCouponJpaRepository userCouponJpaRepository;

    public CouponRepositoryImpl(CouponJpaRepository couponJpaRepository, UserCouponJpaRepository userCouponJpaRepository) {
        this.couponJpaRepository = couponJpaRepository;
        this.userCouponJpaRepository = userCouponJpaRepository;
    }

    @Override
    public Coupon findById(Long id) {
        return couponJpaRepository.findById(id).orElseThrow(NoResultException::new);
    }

    @Override
    public Coupon save(Coupon coupon) {
        return couponJpaRepository.save(coupon);
    }

    @Override
    public Coupon checkExistById(Long id) {
        if (!couponJpaRepository.existsById(id)) {
            throw new NoResultException("Coupon not found with id: " + id);
        }
        return couponJpaRepository.findById(id).orElseThrow(NoResultException::new);
    }

    @Override
    public UserCoupon findUserCouponById(Long userId, Long couponId) {
        return userCouponJpaRepository.findByUserIdAndCoupon_Id(userId, couponId)
                .orElseThrow(NoResultException::new);
    }

    @Override
    public UserCoupon saveUserCoupon(UserCoupon userCoupon) {
        return userCouponJpaRepository.save(userCoupon);
    }

    @Override
    public void deleteUsedCoupon(UserCoupon userCoupon) {
        userCouponJpaRepository.delete(userCoupon);
    }

    @Override
    public Optional<Coupon> findByPessimisticLock(Long couponId) {
        return couponJpaRepository.findByPessimisticLock(couponId);
    }

    @Override
    public void flush() {
        couponJpaRepository.flush();
    }


}
