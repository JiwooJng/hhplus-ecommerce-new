package kr.hhplus.be.test.product;


import kr.hhplus.be.domain.product.ProductService;
import kr.hhplus.be.domain.product.repository.ProductCacheRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Testcontainers
public class ProductRedisServiceTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCacheRepository productCacheRepository;

    private final String SALES_KEY = "product:sales";

    @Test
    public void 상품판매_정보_업데이트_성공() {
        // given
        long productId = 1L;
        long salesAmount = 10L;
        Double score = productCacheRepository.getScore(SALES_KEY + LocalDate.now(), Long.toString(productId));

        if (score == null) {
            productCacheRepository.saveIfAbsent(SALES_KEY + LocalDate.now(), Long.toString(productId), 20);
        }
        productService.updateProductInfo(productId, salesAmount);
        Double updateScore = productCacheRepository.getScore(SALES_KEY + LocalDate.now(), Long.toString(productId));

        assertThat(updateScore).isEqualTo(30.0);
    }

}
