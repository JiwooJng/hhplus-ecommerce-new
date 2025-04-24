package kr.hhplus.be.domain.order.entity;

import jakarta.persistence.*;
import kr.hhplus.be.application.OrderItemRequest;
import kr.hhplus.be.domain.product.Product;

import java.math.BigDecimal;

@Entity
public class OrderItem {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne @JoinColumn(name = "order_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Order order;
    @ManyToOne @JoinColumn(name = "product_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Product product;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    public OrderItem(Order order, OrderItemRequest itemRequest) {
        this.order = order;
        this.product = itemRequest.getProduct();
        this.quantity = itemRequest.getQuantity();

        this.totalPrice = calculateTotalPrice();
    }
    public OrderItem() {
        // JPA에서 사용하기 위한 기본 생성자
    }

    public BigDecimal calculateTotalPrice() {
        return this.product.calculateProductPrice(this.quantity);
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
