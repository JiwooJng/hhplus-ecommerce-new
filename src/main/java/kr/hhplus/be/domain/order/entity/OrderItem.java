package kr.hhplus.be.domain.order.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class OrderItem {
    @Id @GeneratedValue
    private Long id;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "product_price")
    private BigDecimal productPrice;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    public OrderItem(Long productId, BigDecimal price, Integer quantity) {
        this.productId = productId;
        this.productPrice = price;
        this.quantity = quantity;
        calculateTotalPrice();
    }
    public OrderItem() {
        // JPA에서 사용하기 위한 기본 생성자
    }

    public void calculateTotalPrice() {
        this.totalAmount = this.productPrice.multiply(new BigDecimal(this.quantity));
    }

    public Long getProductId() {
        return this.productId;
    }
    public Integer getQuantity() {
        return this.quantity;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }
}
