package kr.hhplus.be.interfaces.order;

import kr.hhplus.be.application.order.OrderItemInfo;

import java.math.BigDecimal;


public class OrderItemRequest {
    private Long productId;
    private Integer quantity;
    private BigDecimal price;

    public OrderItemRequest(Long productId, BigDecimal price, Integer quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }
    public Long getProductId() {
        return productId;
    }
    public  Integer getQuantity() {
        return quantity;
    }

    public OrderItemInfo toOrderItemInfo() {
        return new OrderItemInfo(productId, price, quantity);
    }

}
