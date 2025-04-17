package kr.hhplus.be.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;

@Entity
public class ProductStock {
    @Id @GeneratedValue
    private Long id;

    @OneToOne
    private Product product;
    private int quantity;

    private LocalDateTime updateDate;

    public ProductStock(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.updateDate = LocalDateTime.now();

    }

    public ProductStock() {
        // JPA에서 사용하기 위한 기본 생성자
    }

    public boolean check(Integer quantity) {
        return this.quantity >= quantity;
    }

}
