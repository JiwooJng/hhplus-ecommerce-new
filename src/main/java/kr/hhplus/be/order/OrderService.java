package kr.hhplus.be.order;


import kr.hhplus.be.coupon.entity.Coupon;
import kr.hhplus.be.order.entity.Order;
import kr.hhplus.be.order.entity.OrderItem;
import kr.hhplus.be.order.repository.OrderItemRepository;
import kr.hhplus.be.order.repository.OrderRepository;
import kr.hhplus.be.user.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public void addOrderItem(Order order, Long productId, Integer quantity) {
        OrderItem orderItem = new OrderItem(order, productId, quantity);
        orderItemRepository.save(orderItem);
    }

    public Order create(User user) {
        Order order = new Order(user);
        return orderRepository.save(order);

    }

    public BigDecimal applyCoupon(Order order, Coupon coupon) {
        BigDecimal finalPrice = order.applyCoupon(coupon);
        orderRepository.save(order);

        return finalPrice;
    }

    public void updateTotalPrice(Order order, BigDecimal totalPrice) {
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
    }
    public void updateFinalPrice(Order order, BigDecimal totalPrice) {
        order.setFinalPrice(totalPrice);
        orderRepository.save(order);
    }
}
