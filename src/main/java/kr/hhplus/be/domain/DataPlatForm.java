package kr.hhplus.be.domain;

import kr.hhplus.be.application.order.OrderInfo;


public interface DataPlatForm {
    void send(OrderInfo orderInfo);
}
