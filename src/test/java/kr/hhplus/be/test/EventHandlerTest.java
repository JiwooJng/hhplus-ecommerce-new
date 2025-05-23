package kr.hhplus.be.test;


import kr.hhplus.be.application.order.OrderInfo;
import kr.hhplus.be.application.order.OrderItemInfo;
import kr.hhplus.be.application.order.OrderItemInfoList;
import kr.hhplus.be.application.payment.PaymentEventHandler;
import kr.hhplus.be.domain.DataPlatForm;
import kr.hhplus.be.domain.payment.PaymentSuccessEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EventHandlerTest {
    @Mock
    private DataPlatForm dataPlatForm;
    @InjectMocks
    private PaymentEventHandler paymentEventHandler;

    @Test
    void 이벤트_핸들러가_정상적으로_실행된다() {
        long userId = 1L;
        long orderId = 1L;
        BigDecimal orderAmount = BigDecimal.valueOf(100000);

        // 주문 정보
        List<OrderItemInfo> orderItemList = new ArrayList<>();
        orderItemList.add(new OrderItemInfo(1L, BigDecimal.valueOf(10000), 5));
        orderItemList.add(new OrderItemInfo(2L, BigDecimal.valueOf(50000), 2));
        OrderInfo orderInfo = new OrderInfo(orderId, userId, null, orderAmount, BigDecimal.ZERO, orderAmount, LocalDateTime.now(), new OrderItemInfoList(orderItemList));

        PaymentSuccessEvent event = new PaymentSuccessEvent(orderInfo);

        // When
        paymentEventHandler.paymentSuccessHandler(event);

        // Then
        verify(dataPlatForm, times(1)).send(orderInfo);
    }

}
