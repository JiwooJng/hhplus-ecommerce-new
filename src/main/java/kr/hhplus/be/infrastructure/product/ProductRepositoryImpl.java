package kr.hhplus.be.infrastructure.product;

import jakarta.persistence.NoResultException;
import kr.hhplus.be.domain.product.Product;
import kr.hhplus.be.domain.product.repository.ProductRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductJpaRepository productJpaRepository;
    private final RedisTemplate<String, Object> redisTemplate;



    public ProductRepositoryImpl(ProductJpaRepository productJpaRepository, RedisTemplate<String, Object> redisTemplate) {
        this.productJpaRepository = productJpaRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll();
    }
    @Override
    public Product findById(Long id) {
        return productJpaRepository.findById(id).orElseThrow(() -> new NoResultException("Product not found with id: " + id));
    }
    @Override
    public Product findByName(String name) {
        return productJpaRepository.findByName(name).orElseThrow(() -> new NoResultException("Product not found with name: " + name));
    }
    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Optional<Product> findByPessimisticLock(Long id) {
        return productJpaRepository.findByPessimisticLock(id);
    }


    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
    @Override
    public boolean saveIfAbsent(String key, String value, double score) {
        return redisTemplate.opsForZSet().addIfAbsent(key, value, score);
    }
    @Override
    public double getScore(String key, String value) {
        return redisTemplate.opsForZSet().score(key, value);
    }
    @Override
    public void increment(String key, String value, long score) {
        redisTemplate.opsForZSet().incrementScore(key, value, score);
    }
    @Override
    public Set<String> getBestSellers(String key, int start, int end) {
        return redisTemplate.opsForZSet().range(key, start, end).stream()
                .map(Object::toString)
                .collect(Collectors.toSet());
    }
    @Override
    public void unionAndStore(String key, List subKeys, String destinationKey) {
        redisTemplate.opsForZSet().unionAndStore(key, subKeys, destinationKey);
    }
}
