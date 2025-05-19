package kr.hhplus.be.test.product;

import kr.hhplus.be.domain.product.Product;
import kr.hhplus.be.domain.product.repository.ProductRepository;
import kr.hhplus.be.domain.product.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceUnitTest {
    @Mock
    ProductRepository productRepository;
    @InjectMocks
    ProductService productService;

    private final String productName = "망고";
    private final Long stock = 30L;

    //TODO: 상품을 등록할 수 있다.
    @Test
    void 상품_등록() {
        // given
        Product mockProduct = new Product("망고", BigDecimal.valueOf(30000), stock);
        when(productRepository.save(any(Product.class)))
                .thenReturn(mockProduct);
        // when
        Product returnProduct = productService.registerProduct("망고", BigDecimal.valueOf(30000), stock);
        // then

        Assertions.assertEquals(mockProduct, returnProduct);
        verify(productRepository).save(any(Product.class));
    }
    //TODO: 상품 목록을 조회할 수 있다.
    @Test
    void 상품_목록_조회() {
        // given
        List<Product> products = List.of(
                new Product("망고", BigDecimal.valueOf(30000), stock),
                new Product("딸기", BigDecimal.valueOf(20000), stock)
        );
        when(productRepository.findAll())
                .thenReturn(products);
        // when
        List<Product> returnProducts = productService.getAll();
        // then
        assert returnProducts.size() == 2;
        assert returnProducts.get(0).isEqualName("망고");
        assert returnProducts.get(1).isEqualName("딸기");
        assert returnProducts.get(0).isEqualPrice(BigDecimal.valueOf(30000));
        assert returnProducts.get(1).isEqualPrice(BigDecimal.valueOf(20000));

    }

    // TODO: 상품 명세를 조회할 수 있다.
    @Test
    void 상품_명세_조회() {
        // given
        Product product = new Product("망고", BigDecimal.valueOf(30000), stock);
        when(productRepository.findByName(productName))
                .thenReturn(product);
        // when
        Product returnProduct = productService.getProduct(productName);
        // then
        assert returnProduct.isEqualName("망고");
        assert returnProduct.isEqualPrice(BigDecimal.valueOf(30000));
    }

}
