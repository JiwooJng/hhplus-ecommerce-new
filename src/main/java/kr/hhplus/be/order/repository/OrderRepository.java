package kr.hhplus.be.order.repository;

import kr.hhplus.be.order.entity.Order;

public interface OrderRepository {
    Order findById(Long orderId);

    Order save(Order order);
}
