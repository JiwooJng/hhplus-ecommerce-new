package kr.hhplus.be.application.payment;

import kr.hhplus.be.domain.DataPlatForm;
import kr.hhplus.be.domain.payment.PaymentSuccessEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PaymentEventHandler {
    private final DataPlatForm dataPlatForm;

    public PaymentEventHandler(DataPlatForm dataPlatForm) {
        this.dataPlatForm = dataPlatForm;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void paymentSuccessHandler(PaymentSuccessEvent event) {
        PaymentSuccessEvent successEvent = new PaymentSuccessEvent(event.getOrderInfo());
        try {
            dataPlatForm.send(event.getOrderInfo());
        } catch (Exception e) {

        }
    }



}
