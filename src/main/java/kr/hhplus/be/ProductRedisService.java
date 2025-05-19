package kr.hhplus.be;

import kr.hhplus.be.domain.product.Product;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class ProductRedisService {

    private final String SALES_KEY = "product:sales";
    private StringRedisTemplate redisTemplate;

    public ProductRedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    public void updateProductInfo(Long productId, Long salesAmount) {
        // Redis에 상품 정보 업데이트
        // 오늘의 키
        String todaySalesKey = SALES_KEY + LocalDate.now();

        // 상품 정보가 Redis에 존재하지 않는 경우
        // ZSet에 상품 ID와 판매량을 추가
        boolean isAdded = redisTemplate.opsForZSet().addIfAbsent(todaySalesKey, productId.toString(), salesAmount);
        if (!isAdded) {
            // 상품 정보가 Redis에 존재하는 경우
            // ZSet의 판매량을 증가
            redisTemplate.opsForZSet().incrementScore(todaySalesKey, productId.toString(), salesAmount);
        }
    }
    public Set<String>getTopProductsByDate(LocalDate date, int topN) {
        // ZSet에서 판매량이 가장 높은 상품 ID를 가져옴
        String todaySalesKey = SALES_KEY + date;
        return redisTemplate.opsForZSet().reverseRange(todaySalesKey, 0, topN - 1);
    }

    public Set<String> getTopProductForDays(int days, int topN) {
        List<String> topSalesKey = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            String dateKey = SALES_KEY + LocalDate.now().minusDays(i);
            topSalesKey.add(dateKey);
        }

        String tempKey = SALES_KEY + "temp";
        redisTemplate.opsForZSet().unionAndStore(topSalesKey.get(0), topSalesKey.subList(1, topSalesKey.size()), tempKey);

        Set<String> topProductsForDays = redisTemplate.opsForZSet().reverseRange(tempKey, 0, topN - 1);
        // 임시 키 삭제
        redisTemplate.delete(tempKey);

        return topProductsForDays;
    }

}
