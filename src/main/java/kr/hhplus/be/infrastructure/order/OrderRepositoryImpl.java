package kr.hhplus.be.infrastructure.order;

import jakarta.persistence.NoResultException;
import kr.hhplus.be.domain.order.entity.Order;
import kr.hhplus.be.domain.order.entity.OrderItem;
import kr.hhplus.be.domain.order.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderJpaRepository orderJpaRepository;
    private final OrderItemJpaRepository orderItemJpaRepository;

    public OrderRepositoryImpl(OrderJpaRepository orderJpaRepository, OrderItemJpaRepository orderItemJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
        this.orderItemJpaRepository = orderItemJpaRepository;
    }

    @Override
    public Order findById(Long orderId) {
        return orderJpaRepository.findById(orderId)
                .orElseThrow(NoResultException::new);
    }

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }

    @Override
    public List<OrderItem> findOrderItemsById(Long orderId) {
        return orderItemJpaRepository.findOrderItemsById(orderId);
    }
    @Override
    public void save(OrderItem orderItem) {
        orderItemJpaRepository.save(orderItem);
    }
}