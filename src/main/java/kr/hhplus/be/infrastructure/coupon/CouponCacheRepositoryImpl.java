package kr.hhplus.be.infrastructure.coupon;


import kr.hhplus.be.domain.coupon.repository.CouponCacheRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class CouponCacheRepositoryImpl implements CouponCacheRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    public CouponCacheRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }
    @Override
    public boolean saveIfAbsent(String key, String value, double score) {
        return redisTemplate.opsForZSet().addIfAbsent(key, value, score);
    }
    @Override
    public long decrement(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }
    @Override
    public Set<String> fetchFromSet(String key, long batchSize) {
        return redisTemplate.opsForZSet().range(key, 0, batchSize - 1).stream()
                .map(Object::toString)
                .collect(Collectors.toSet());
    }
    @Override
    public void removeFromSet(String key, String value) {
        redisTemplate.opsForZSet().remove(key, value);
    }
    @Override
    public double getScore(String key, String value) {
        return redisTemplate.opsForZSet().score(key, value);
    }
    @Override
    public boolean exists(String key, String value) {
        Double score = getScore(key, value);
        if (score != null) {
            return true;
        }
        return false;
    }
}
