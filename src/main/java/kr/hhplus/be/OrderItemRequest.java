package kr.hhplus.be;

import kr.hhplus.be.product.entity.Product;

public class OrderItemRequest {
    private Long productId;
    private Integer quantity;

    public OrderItemRequest(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }
    public Integer getQuantity() {
        return quantity;
    }

}
