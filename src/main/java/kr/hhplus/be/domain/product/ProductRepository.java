package kr.hhplus.be.domain.product;

import java.util.List;

public interface ProductRepository {
    Product findByName(String name);
    Product findById(Long id);
    List<Product> findAll();

    Product save(Product product);

    Product findByPessimisticLock(Long id);
}
