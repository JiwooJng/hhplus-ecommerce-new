package kr.hhplus.be.product.repository;

import kr.hhplus.be.product.entity.ProductStock;

public interface ProductStockRepository {
    ProductStock findById(Long productId);
    void save(ProductStock productStock);
}
