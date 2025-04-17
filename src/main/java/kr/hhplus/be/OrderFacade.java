package kr.hhplus.be;

import jakarta.transaction.Transactional;
import kr.hhplus.be.coupon.CouponService;
import kr.hhplus.be.coupon.entity.Coupon;
import kr.hhplus.be.order.OrderService;
import kr.hhplus.be.order.entity.Order;
import kr.hhplus.be.product.ProductService;
import kr.hhplus.be.user.User;
import kr.hhplus.be.user.UserService;

import java.math.BigDecimal;
import java.util.List;

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

    @Transactional
    public Order createOrder(Long userId, List<OrderItemRequest> orderItemRequests, Long couponId) {
        // 유저 확인
        User user = userService.findById(userId);
        // 주문 생성
        Order order = orderService.create(user);

        // 주문 항목 생성
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItemRequest request : orderItemRequests) {
            Long productId = request.getProductId();
            Integer quantity = request.getQuantity();
            if (!productService.checkStock(productId, quantity)) {
                throw new IllegalArgumentException("재고가 부족합니다.");
            }
            BigDecimal productPrice = productService.calculateProductPrice(productId, quantity);
            totalPrice = totalPrice.add(productPrice);

            orderService.addOrderItem(order, productId, quantity);

        }
        orderService.updateTotalPrice(order, totalPrice);

        // 쿠폰 적용 전 총 가격
        BigDecimal finalPrice = totalPrice;
        // 쿠폰 적용 시
        if (couponId != null) {
            if (!couponService.isExist(couponId)) {
                throw new IllegalArgumentException("쿠폰이 존재하지 않습니다.");
            }
            Coupon coupon = couponService.use(userId, couponId);
            finalPrice = orderService.applyCoupon(order, coupon); // 쿠폰 할인 금액 적용 후 최종 가격
        }

        // 최종 결제 금액
        orderService.updateFinalPrice(order, finalPrice);

        return order;
    }

}
