package kr.hhplus.be.domain.payment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Payment pay(BigDecimal amount) {
        Payment payment = new Payment(amount);
        payment.complete();
        return paymentRepository.save(payment);
    }

}
