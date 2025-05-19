package kr.hhplus.be.domain.coupon.repository;

import java.util.Set;

public interface CouponCacheRepository {

    void save(String key, Object value);

    boolean saveIfAbsent(String key, String value, double score);

    long decrement(String key);

    Set<String> fetchFromSet(String key, long batchSize);

    void removeFromSet(String key, String value);

    double getScore(String key, String value);

    boolean exists(String key, String value);
}
