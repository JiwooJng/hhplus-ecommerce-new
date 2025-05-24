package kr.hhplus.be.application.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.hhplus.be.domain.order.entity.Order;
import kr.hhplus.be.domain.order.entity.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderInfo {
    private Long orderId;
    private Long userId;
    private Long appliedCouponId;
    private BigDecimal orderAmount;
    private BigDecimal discountAmount;
    private BigDecimal payAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderDateTime;
    private OrderItemInfoList orderItemsInfo;

    public OrderInfo(Long orderId, Long userId, Long appliedCouponId,
                     BigDecimal orderAmount, BigDecimal discountAmount,
                     BigDecimal payAmount, LocalDateTime orderDateTime,
                     OrderItemInfoList orderItemsInfo) {
        this.orderId = orderId;
        this.userId = userId;
        this.appliedCouponId = appliedCouponId;
        this.orderAmount = orderAmount;
        this.discountAmount = discountAmount;
        this.payAmount = payAmount;
        this.orderDateTime = orderDateTime;
        this.orderItemsInfo = orderItemsInfo;
    }
    public OrderInfo() {
        // 기본 생성자
    }
    public static OrderInfo fromOrder(Order order, List<OrderItem> orderItems) {
        OrderInfo orderInfo = new OrderInfo();

        orderInfo.orderId = order.getId();
        orderInfo.userId = order.getUserId();
        orderInfo.appliedCouponId = order.getAppliedCouponId();
        orderInfo.orderAmount = order.getOrderAmount();
        orderInfo.discountAmount = order.getDiscountAmount();
        orderInfo.payAmount = order.getPayAmount();
        orderInfo.orderDateTime = order.getOrderDate();
        orderInfo.orderItemsInfo = OrderItemInfoList.fromOrderItems(orderItems);

        return orderInfo;
    }
}
