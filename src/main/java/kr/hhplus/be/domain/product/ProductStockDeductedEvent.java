package kr.hhplus.be.domain.product;

public class ProductStockDeductedEvent {
    private Long productId;
    private Integer quantity;

    public ProductStockDeductedEvent(Long productId, Integer quantity) {
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
