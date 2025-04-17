package kr.hhplus.be.product.repository;

import kr.hhplus.be.product.entity.Product;

import java.util.List;

public interface ProductRepository {
    Product findByName(String name);
    List<Product> findAll();

    Product save(Product product);

}
