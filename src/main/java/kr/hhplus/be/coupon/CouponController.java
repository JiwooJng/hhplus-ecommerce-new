package kr.hhplus.be.coupon;


import kr.hhplus.be.coupon.entity.UserCoupon;
import kr.hhplus.be.coupon.enumtype.CouponType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {
    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
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
    public boolean useCoupon(@PathVariable Long userId, @RequestBody Long couponId) {
       return couponService.use(userId, couponId);
    }
}
