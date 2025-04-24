package kr.hhplus.be.order.repository;

import kr.hhplus.be.order.entity.OrderItem;

import java.util.List;

public interface OrderItemRepository {
    List<OrderItem> findById(Long orderId);
    OrderItem save(OrderItem orderItem);
}
