package kr.hhplus.be.payment;

public interface PaymentRepository {
    Payment findById(Long paymentId);
    Payment save(Payment payment);
}
