package kr.hhplus.be.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

@Entity
public class OrderItem {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    private Order order;
    private Long productId;
    private Integer quantity;
    private BigDecimal totalPrice;

    public OrderItem(Order order, Long productId, Integer quantity) {
        this.order = order;
        this.productId = productId;
        this.quantity = quantity;
    }
    public OrderItem() {
        // JPA에서 사용하기 위한 기본 생성자
    }

}
