package kr.hhplus.be.infrastructure;

import kr.hhplus.be.application.order.OrderInfo;
import kr.hhplus.be.domain.DataPlatForm;
import org.springframework.stereotype.Component;

@Component
public class DataPlatFormCli implements DataPlatForm {
    @Override
    public void send(OrderInfo orderInfo) {

    }
}
