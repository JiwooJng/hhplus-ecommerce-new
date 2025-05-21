package kr.hhplus.be.domain.product.repository;

import kr.hhplus.be.domain.product.Product;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository {
    Product findByName(String name);
    Product findById(Long id);
    List<Product> findAll();

    Product save(Product product);

    Optional<Product> findByPessimisticLock(Long id);


    void delete(String key);
    boolean saveIfAbsent(String key, String value, double score);
    double getScore(String key, String value);
    void increment(String key, String value, long score);
    Set<String> getBestSellers(String key, int start, int end);
    void unionAndStore(String key, List subKeys, String destinationKey);

}
