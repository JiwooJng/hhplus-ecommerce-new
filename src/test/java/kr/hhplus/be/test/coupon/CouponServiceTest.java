package kr.hhplus.be.test.coupon;

import kr.hhplus.be.coupon.*;
import kr.hhplus.be.coupon.entity.Coupon;
import kr.hhplus.be.coupon.entity.CouponIssue;
import kr.hhplus.be.coupon.enumtype.CouponType;
import kr.hhplus.be.coupon.repository.CouponIssueRepository;
import kr.hhplus.be.coupon.repository.CouponRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {
    @Mock
    CouponRepository couponRepository;
    @Mock
    CouponIssueRepository couponIssueRepository;

    @InjectMocks
    CouponService couponService;

    private final Long couponId = 1L;
    private final Long userId = 1L;
    private final Long maxIssueAmount = 1000L;

    @Test
    void 쿠폰_발급_성공() {
        // given
        when(couponRepository.findById(couponId))
                .thenReturn(new Coupon(CouponType.선착순_쿠폰, maxIssueAmount));

        // when
        couponService.issue(couponId, userId);
        // then
        assert true;
    }

    @Test
    void 쿠폰_사용_성공() {
        // given
        when(couponIssueRepository.findById(userId, couponId))
                .thenReturn(new CouponIssue(couponId, userId));

        // when
        boolean result = couponService.use(userId, couponId);

        // then
        Assertions.assertTrue(result);
        verify(couponIssueRepository).findById(userId, couponId);

        assert true;
    }

}
