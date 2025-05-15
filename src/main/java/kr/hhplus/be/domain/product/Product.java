package kr.hhplus.be.domain.product;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class Product implements Serializable {
    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Long productStock;

    public Product(String name, BigDecimal price, Long productStock) {
        this.name = name;
        this.price = price;
        this.productStock = productStock;
    }

    public Product() {
        // JPA에서 사용하기 위한 기본 생성자
    }

    public boolean isEqualName(String name) {
        return this.name.equals(name);
    }

    public boolean isEqualPrice(BigDecimal price) {
        return this.price.equals(price);
    }

    public boolean check(Integer quantity) {
        return this.productStock >= quantity;
    }
    public Long getStock() {
        return productStock;
    }

    public BigDecimal calculateProductPrice(Integer quantity) {
        return this.price.multiply(new BigDecimal(quantity));
    }

    public void decreaseStock(Integer quantity) {
        productStock -= quantity;
    }

    public Long getId() {
        return id;
    }
}
