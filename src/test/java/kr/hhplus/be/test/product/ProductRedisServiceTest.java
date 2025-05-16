package kr.hhplus.be.test.product;


import kr.hhplus.be.ProductRedisService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@Testcontainers
public class ProductRedisServiceTest {

    @Autowired
    private ProductRedisService productRedisService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    private final String SALES_KEY = "product:sales";

    long productId = 1L;
    long salesAmount = 10L;

    @Test
    public void 상품판매_정보_판매내역에_있는_상품이면_업데이트() {
        Double score = redisTemplate.opsForZSet().score(SALES_KEY + LocalDate.now(), Long.toString(productId));

        if (score == null) {
            redisTemplate.opsForZSet().add(SALES_KEY + LocalDate.now(), Long.toString(productId), 20);
        }
        productRedisService.updateProductInfo(productId, salesAmount);
        Double updateScore = redisTemplate.opsForZSet().score(SALES_KEY + LocalDate.now(), Long.toString(productId));

        assertThat(updateScore).isEqualTo(30.0);
    }

    @Test
    public void 상품판매_정보_판매내역에_없는_상품이면_새로_추가() {
        Double score = redisTemplate.opsForZSet().score(SALES_KEY + LocalDate.now(), Long.toString(productId));

        if (score != null) {
            redisTemplate.opsForZSet().remove(SALES_KEY + LocalDate.now(), Long.toString(productId));
        }
        productRedisService.updateProductInfo(productId, salesAmount);
        Double updateScore = redisTemplate.opsForZSet().score(SALES_KEY + LocalDate.now(), Long.toString(productId));

        assertThat(updateScore).isEqualTo(10.0);
    }

    @Test
    void 인기상품_Today_조회() {
        productRedisService.updateProductInfo(1L, 10L);
        productRedisService.updateProductInfo(2L, 20L);
        productRedisService.updateProductInfo(3L, 30L);

        Set<String> topProductsToday = productRedisService.getTopProductsByDate(LocalDate.now(), 2);

        assertThat(topProductsToday.size()).isEqualTo(2);
        assertThat(topProductsToday.contains("3")).isEqualTo(true);
    }

}
