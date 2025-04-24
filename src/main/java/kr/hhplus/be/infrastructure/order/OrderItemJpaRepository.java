package kr.hhplus.be.infrastructure.order;

import kr.hhplus.be.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemJpaRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findOrderItemsById(Long orderId);
}
