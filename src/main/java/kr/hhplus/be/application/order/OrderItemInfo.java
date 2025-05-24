package kr.hhplus.be.application.order;

import kr.hhplus.be.domain.order.entity.OrderItem;

import java.math.BigDecimal;

public class OrderItemInfo {
    private Long productId;
    private BigDecimal price;
    private Integer quantity;

    public OrderItemInfo(Long productId, BigDecimal price, Integer quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderItem toOrderItem() {
        return new OrderItem(this.productId, this.price, this.quantity);
    }

}
