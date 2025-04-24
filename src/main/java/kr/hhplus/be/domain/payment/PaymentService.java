package kr.hhplus.be.domain.payment;

import kr.hhplus.be.domain.order.entity.Order;
import kr.hhplus.be.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Payment pay(User user, Order order) {
        Payment payment = new Payment(user, order);
        payment.complete();
        return paymentRepository.save(payment);
    }

}
