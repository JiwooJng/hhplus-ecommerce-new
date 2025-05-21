package kr.hhplus.be.test.product;

import kr.hhplus.be.domain.product.repository.ProductRepository;
import kr.hhplus.be.domain.product.ProductService;
import kr.hhplus.be.server.DatabaseCleanup;
import kr.hhplus.be.server.RedisCleanup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Testcontainers
public class ProductCacheTest {

    @Autowired
    private ProductService productService;
    @MockitoSpyBean
    private ProductRepository productRepository;

    @Autowired
    private DatabaseCleanup databaseCleanup;
    @Autowired
    private RedisCleanup redisCleanup;

    @BeforeEach
    void setUp() {
        databaseCleanup.truncateAllTables();
        redisCleanup.flushAll();
    }

    @Test
    void 상품_조회_캐시_테스트() {

        productService.registerProduct("망고", BigDecimal.valueOf(30000), 10L);

        productService.getAll();
        productService.getAll();
        productService.getAll();
        productService.getAll();

        verify(productRepository, times(1)).findAll();

    }
}
