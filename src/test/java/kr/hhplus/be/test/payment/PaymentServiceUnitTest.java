package kr.hhplus.be.test.payment;

import kr.hhplus.be.order.entity.Order;
import kr.hhplus.be.payment.Payment;
import kr.hhplus.be.payment.PaymentRepository;
import kr.hhplus.be.payment.PaymentService;
import kr.hhplus.be.user.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceUnitTest {
    @Mock
    private PaymentRepository paymentRepository;
    @InjectMocks
    private PaymentService paymentService;

    private final User user = new User("Jiwoo");
    private final Order order = new Order(user);
    private final Payment payment = new Payment(user, order);


    @Test
    void 결제_요청_성공() {
        // given
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        // when
        Payment savedPayment = paymentService.pay(user, order);
        // then
        assertEquals(payment, savedPayment);
    }

    @Test
    void 결제_완료() {
        // given
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        // when
        Payment completePayment = paymentService.complete(payment);
        // then
        assertEquals(payment, completePayment);

    }

}
