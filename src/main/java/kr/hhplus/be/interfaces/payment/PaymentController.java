package kr.hhplus.be.interfaces.payment;


import kr.hhplus.be.application.payment.PaymentFacade;
import kr.hhplus.be.domain.payment.Payment;
import kr.hhplus.be.interfaces.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PaymentController {
    private final PaymentFacade paymentFacade;

    public PaymentController(PaymentFacade paymentFacade) {
        this.paymentFacade = paymentFacade;
    }
    @PostMapping("/payments")
    public ApiResponse<Payment> payment(@RequestBody PaymentRequest request) {
        paymentFacade.payment(request.toCommand());
        return ApiResponse.success(null);
    }
}
