package kr.hhplus.be.test.order;

import kr.hhplus.be.order.entity.Order;
import kr.hhplus.be.order.repository.OrderRepository;
import kr.hhplus.be.order.OrderService;
import kr.hhplus.be.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OrderServiceUnitTest {
    @Mock
    OrderRepository orderRepository;
    @InjectMocks
    OrderService orderService;

    private final User user = new User("Jiwoo");
    private final Order order = new Order(user);

    @Test
    void 주문_생성_성공() {

        // given
        when(orderRepository.save(any(Order.class)))
                .thenReturn(order);
        // when
        Order createdOrder = orderService.create(user);
        // then
        Assertions.assertEquals(order, createdOrder);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void 주문_생성_실패() {
        // given
        when(orderRepository.save(any(Order.class)))
                .thenThrow(new RuntimeException("주문 생성 실패"));

        Exception exception = assertThrows(RuntimeException.class, () ->
                orderService.create(user));
        // then
        Assertions.assertEquals(exception.getMessage(), "주문 생성 실패");
    }
}
