package kr.hhplus.be.product.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;

public class Product {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private BigDecimal price;

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public boolean isEqualName(String name) {
        return this.name.equals(name);
    }

    public boolean isEqualPrice(BigDecimal price) {
        return this.price.equals(price);
    }

}
