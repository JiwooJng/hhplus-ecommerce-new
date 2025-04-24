package kr.hhplus.be.domain.payment;

import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository {
    Payment findById(Long paymentId);
    Payment save(Payment payment);
}
