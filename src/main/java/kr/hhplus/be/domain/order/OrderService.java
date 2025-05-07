package kr.hhplus.be.domain.order;


import kr.hhplus.be.application.OrderItemRequest;
import kr.hhplus.be.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.domain.order.entity.Order;
import kr.hhplus.be.domain.order.entity.OrderItem;
import kr.hhplus.be.domain.order.repository.OrderRepository;
import kr.hhplus.be.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /*
    * 주문 생성
    * 1. 주문 상품 리스트
    * 2. 쿠폰 */
    @Transactional
    public Order create(User user, UserCoupon userCoupon, List<OrderItemRequest> orderItemRequests) {
        Order order = new Order(user);
        order.applyCoupon(userCoupon);

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItemRequest itemRequest : orderItemRequests) {
            OrderItem orderItem = new OrderItem(order, itemRequest);
            orderRepository.save(orderItem);

            totalPrice = totalPrice.add(orderItem.getTotalPrice());
        }
        order.calculateFinalPrice(totalPrice); // 최종 금액 계산

        return orderRepository.save(order);
    }

}
