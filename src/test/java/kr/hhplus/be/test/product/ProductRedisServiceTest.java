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

    @Test
    public void 상품판매_정보_업데이트_성공() {
        // given
        long productId = 1L;
        long salesAmount = 10L;
        Double score = redisTemplate.opsForZSet().score(SALES_KEY + LocalDate.now(), Long.toString(productId));

        if (score == null) {
            redisTemplate.opsForZSet().addIfAbsent(SALES_KEY + LocalDate.now(), Long.toString(productId), 20);
        }
        productRedisService.updateProductInfo(productId, salesAmount);
        Double updateScore = redisTemplate.opsForZSet().score(SALES_KEY + LocalDate.now(), Long.toString(productId));

        assertThat(updateScore).isEqualTo(30.0);
    }

}
