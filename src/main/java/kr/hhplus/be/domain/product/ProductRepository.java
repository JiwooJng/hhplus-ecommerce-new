package kr.hhplus.be.domain.product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product findByName(String name);
    Product findById(Long id);
    List<Product> findAll();

    Product save(Product product);

    Optional<Product> findByPessimisticLock(Long id);
}
