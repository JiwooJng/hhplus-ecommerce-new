package kr.hhplus.be.domain.order.repository;

import kr.hhplus.be.domain.order.entity.Order;
import kr.hhplus.be.domain.order.entity.OrderItem;

import java.util.List;

public interface OrderRepository {
    Order findById(Long orderId);
    Order save(Order order);

    List<OrderItem> findOrderItemsById(Long orderId);
    void save(OrderItem orderItem);

}
