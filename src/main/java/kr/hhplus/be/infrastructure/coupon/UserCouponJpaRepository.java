package kr.hhplus.be.infrastructure.coupon;

import kr.hhplus.be.domain.coupon.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCouponJpaRepository extends JpaRepository<UserCoupon, Long> {

    Optional<UserCoupon> findByUserIdAndCoupon_Id(Long userId, Long couponId);
}
