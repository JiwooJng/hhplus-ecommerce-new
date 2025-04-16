package kr.hhplus.be.product.repository;

import kr.hhplus.be.product.entity.Product;

import java.util.List;

public interface ProductRepository {
    Product findById(Long id);
    List<Product> findAll();

}
