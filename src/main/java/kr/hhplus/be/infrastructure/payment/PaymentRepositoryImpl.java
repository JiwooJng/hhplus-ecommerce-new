package kr.hhplus.be.infrastructure.payment;


import jakarta.persistence.NoResultException;
import kr.hhplus.be.domain.payment.Payment;
import kr.hhplus.be.domain.payment.PaymentRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    private final PaymentJpaRepository paymentJpaRepository;

    public PaymentRepositoryImpl(PaymentJpaRepository paymentJpaRepository) {
        this.paymentJpaRepository = paymentJpaRepository;
    }

    @Override
    public Payment findById(Long paymentId) {
        return paymentJpaRepository.findById(paymentId)
                .orElseThrow(NoResultException::new);
    }
    @Override
    public Payment save(Payment payment) {
        return paymentJpaRepository.save(payment);
    }

}
