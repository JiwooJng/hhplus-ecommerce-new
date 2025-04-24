package kr.hhplus.be.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.math.BigDecimal;

@Entity
public class Product {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private BigDecimal price;
    @OneToOne
    private ProductStock productStock;

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
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

    public void setStock(ProductStock productStock) {
        this.productStock = productStock;
    }
    public boolean checkStock(Integer quantity) {
        return this.productStock.check(quantity);
    }
    public BigDecimal calculateProductPrice(int quantity) {
        return this.price.multiply(new BigDecimal(quantity));
    }
}
