package kr.hhplus.be.interfaces.order;

import kr.hhplus.be.application.order.OrderCreateCommand;
import kr.hhplus.be.application.order.OrderItemInfo;
import kr.hhplus.be.application.order.OrderItemInfoList;

import java.util.ArrayList;
import java.util.List;

public class OrderRequest {
    private Long userId;
    private Long appliedCouponId;
    private List<OrderItemRequest> orderItems = new ArrayList<>();

    public OrderRequest(Long userId, Long appliedCouponId, List<OrderItemRequest> orderItems) {
        this.userId = userId;
        this.appliedCouponId = appliedCouponId;
        this.orderItems = orderItems;
    }

    public OrderCreateCommand toOrderCreateCommand() {
        List<OrderItemInfo> orderItemInfos = new ArrayList<>();
        for (OrderItemRequest itemRequest : orderItems) {
            orderItemInfos.add(itemRequest.toOrderItemInfo());
        }
        return new OrderCreateCommand(userId, appliedCouponId, new OrderItemInfoList(orderItemInfos));
    }
}
