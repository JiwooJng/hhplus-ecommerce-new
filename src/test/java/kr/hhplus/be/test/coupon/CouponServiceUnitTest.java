package kr.hhplus.be.test.coupon;

import kr.hhplus.be.coupon.*;
import kr.hhplus.be.coupon.entity.Coupon;
import kr.hhplus.be.coupon.entity.UserCoupon;
import kr.hhplus.be.coupon.enumtype.CouponType;
import kr.hhplus.be.coupon.repository.UserCouponRepository;
import kr.hhplus.be.coupon.repository.CouponRepository;
import kr.hhplus.be.user.User;
import kr.hhplus.be.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CouponServiceUnitTest {
    @Mock
    UserRepository userRepository;
    @Mock
    CouponRepository couponRepository;
    @Mock
    UserCouponRepository userCouponRepository;

    @InjectMocks
    CouponService couponService;

    private final Long couponId = 1L;
    private final Long userId = 1L;
    private final Long maxIssueAmount = 1000L;
    private final BigDecimal discountAmount = new BigDecimal(3000);

    //TODO: 쿠폰을 발행할 수 있다.
    @Test
    void 쿠폰_발행_성공() {
        // given
        Coupon coupon = new Coupon(CouponType.선착순_쿠폰, discountAmount, maxIssueAmount);
        when(couponRepository.save(any(Coupon.class)))
                .thenReturn(coupon);

        // when
        couponService.publish(CouponType.선착순_쿠폰, discountAmount, maxIssueAmount);
        // then
        verify(couponRepository).save(any(Coupon.class));
    }

    @Test
    void 쿠폰_발급_성공() {
        // given
        when(couponRepository.findById(couponId))
                .thenReturn(new Coupon(CouponType.선착순_쿠폰, discountAmount, maxIssueAmount));
        // when
        couponService.issue(couponId, userId);
        // then
        assert true;
        verify(userCouponRepository).save(any(UserCoupon.class));
    }

    @Test
    void 쿠폰_사용_성공() {
        // given
        UserCoupon userCoupon = new UserCoupon(new User("Jiwoo"), new Coupon(CouponType.선착순_쿠폰, discountAmount, maxIssueAmount));
        when(userCouponRepository.findById(userId, couponId))
                .thenReturn(userCoupon);
        // when
        couponService.use(userId, couponId);
        // then
        assert !userCoupon.canUse();
        verify(userCouponRepository).delete(any(UserCoupon.class));


    }

}
