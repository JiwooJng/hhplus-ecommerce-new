package kr.hhplus.be.application;

import kr.hhplus.be.domain.product.Product;

public class OrderItemRequest {
    private Product product;
    private Integer quantity;

    public OrderItemRequest(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    public Product getProduct() {
        return product;
    }
    public Integer getQuantity() {
        return quantity;
    }

}
