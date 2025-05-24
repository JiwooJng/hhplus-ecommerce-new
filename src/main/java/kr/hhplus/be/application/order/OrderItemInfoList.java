package kr.hhplus.be.application.order;

import kr.hhplus.be.domain.order.entity.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderItemInfoList {
    private List<OrderItemInfo> orderItemInfoList;

    public List<OrderItemInfo> getOrderItemInfoList() {
        return orderItemInfoList;
    }

    public OrderItemInfoList(List<OrderItemInfo> orderItemInfoList) {
        this.orderItemInfoList = orderItemInfoList;
    }

    public List<OrderItem> toOrderItems() {
        List<OrderItem> orderItems = new ArrayList<>();

        for(OrderItemInfo orderItemInfo : this.orderItemInfoList) {
            orderItems.add(orderItemInfo.toOrderItem());
        }

        return orderItems;
    }
    public static OrderItemInfoList fromOrderItems(List<OrderItem> orderItems) {
        List<OrderItemInfo> orderItemInfoList = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            orderItemInfoList.add(new OrderItemInfo(orderItem.getProductId(), orderItem.getTotalAmount(), orderItem.getQuantity()));
        }

        return new OrderItemInfoList(orderItemInfoList);
    }

}
