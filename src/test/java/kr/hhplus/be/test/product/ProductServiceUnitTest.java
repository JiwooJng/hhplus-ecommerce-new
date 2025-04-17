package kr.hhplus.be.test.product;

import kr.hhplus.be.product.entity.Product;
import kr.hhplus.be.product.repository.ProductRepository;
import kr.hhplus.be.product.ProductService;
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

    //TODO: 상품을 등록할 수 있다.
    @Test
    void 상품_등록() {
        // given
        Product mockProduct = new Product("망고", new BigDecimal(30000));
        when(productRepository.save(any(Product.class)))
                .thenReturn(mockProduct);
        // when
        Product returnProduct = productService.registerProduct("망고", new BigDecimal(30000), 150);
        // then

        Assertions.assertEquals(mockProduct, returnProduct);
        verify(productRepository).save(any(Product.class));
    }
    //TODO: 상품 목록을 조회할 수 있다.
    @Test
    void 상품_목록_조회() {
        // given
        List<Product> products = List.of(
                new Product("망고", new BigDecimal(30000)),
                new Product("딸기", new BigDecimal(20000))
        );
        when(productRepository.findAll())
                .thenReturn(products);
        // when
        List<Product> returnProducts = productService.getAll();
        // then
        assert returnProducts.size() == 2;
        assert returnProducts.get(0).isEqualName("망고");
        assert returnProducts.get(1).isEqualName("딸기");
        assert returnProducts.get(0).isEqualPrice(new BigDecimal(30000));
        assert returnProducts.get(1).isEqualPrice(new BigDecimal(20000));

    }

    // TODO: 상품 명세를 조회할 수 있다.
    @Test
    void 상품_명세_조회() {
        // given
        Product product = new Product("망고", new BigDecimal(30000));
        when(productRepository.findByName(productName))
                .thenReturn(product);
        // when
        Product returnProduct = productService.getProduct(productName);
        // then
        assert returnProduct.isEqualName("망고");
        assert returnProduct.isEqualPrice(new BigDecimal(30000));
    }

}
