package kr.hhplus.be.interfaces;


import kr.hhplus.be.domain.product.Product;
import kr.hhplus.be.domain.product.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 상품 목록 조회
    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    // 상품 명세 조회
    @GetMapping("/{productName}")
    public Product getProduct(@PathVariable String productName) {
        return productService.getProduct(productName);
    }
}
