package kr.hhplus.be.payment;

import kr.hhplus.be.order.entity.Order;
import kr.hhplus.be.user.User;

public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment pay(User user, Order order) {
        Payment payment = new Payment(user, order);
        return paymentRepository.save(payment);
    }

    public Payment complete(Payment payment) {
        payment.complete();
        return paymentRepository.save(payment);
    }

}
