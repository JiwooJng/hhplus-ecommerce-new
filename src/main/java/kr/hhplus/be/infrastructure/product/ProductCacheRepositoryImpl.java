package kr.hhplus.be.infrastructure.product;

import kr.hhplus.be.domain.product.repository.ProductCacheRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ProductCacheRepositoryImpl implements ProductCacheRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    public ProductCacheRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
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
