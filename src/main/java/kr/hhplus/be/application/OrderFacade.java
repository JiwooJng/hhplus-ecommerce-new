package kr.hhplus.be.application;

import kr.hhplus.be.domain.coupon.CouponService;
import kr.hhplus.be.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.domain.order.OrderService;
import kr.hhplus.be.domain.order.entity.Order;
import kr.hhplus.be.domain.product.ProductService;
import kr.hhplus.be.domain.user.User;
import kr.hhplus.be.domain.user.UserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Component
public class OrderFacade {
    private final OrderService orderService;
    private final CouponService couponService;
    private final UserService userService;
    private final ProductService productService;

    public OrderFacade(OrderService orderService, CouponService couponService, UserService userService, ProductService productService) {
        this.orderService = orderService;
        this.couponService = couponService;
        this.userService = userService;
        this.productService = productService;
    }

    public Order createOrder(Long userId, List<OrderItemRequest> orderItemRequests, Long couponId) {
        // 유저 확인
        User user = userService.findById(userId);

        // 재고 확인
        productService.checkStock(orderItemRequests);

        // 쿠폰 확인
        UserCoupon userCoupon = couponService.isExist(userId, couponId);

        // 주문 생성
        Order order = orderService.create(user, userCoupon, orderItemRequests);

        return order;
    }

}
