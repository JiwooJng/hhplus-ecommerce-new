package kr.hhplus.be.domain.product.repository;

import java.util.List;
import java.util.Set;

public interface ProductCacheRepository {
    void delete(String key);
    boolean saveIfAbsent(String key, String value, double score);
    double getScore(String key, String value);
    void increment(String key, String value, long score);
    Set<String> getBestSellers(String key, int start, int end);
    void unionAndStore(String key, List subKeys, String destinationKey);


}
