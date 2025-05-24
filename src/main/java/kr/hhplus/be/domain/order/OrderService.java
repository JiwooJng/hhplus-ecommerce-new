package kr.hhplus.be.domain.order;


import kr.hhplus.be.application.order.OrderInfo;
import kr.hhplus.be.domain.event.EventPublisher;
import kr.hhplus.be.domain.order.entity.Order;
import kr.hhplus.be.domain.order.entity.OrderItem;
import kr.hhplus.be.domain.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final EventPublisher eventPublisher;

    public OrderService(OrderRepository orderRepository, EventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    /*
    * 주문 생성
    * 1. 주문 상품 리스트
    * 2. 쿠폰 */

    public OrderInfo order(Order order, List<OrderItem> orderItems) {
        Order saved = orderRepository.save(order);

        for (OrderItem orderItem : orderItems) {
            orderRepository.save(orderItem);
        }
        eventPublisher.publish(new OrderCreatedEvent(order.getId(), order.getUserId(), order.getAppliedCouponId(), order.getOrderItems()));

        return OrderInfo.fromOrder(order, orderItems);
    }

    public OrderInfo getOrderInfo(Long orderId) {
        Order order = orderRepository.findById(orderId);
        List<OrderItem> orderItems = orderRepository.findOrderItemsById(orderId);

        return OrderInfo.fromOrder(order, orderItems);
    }
}
