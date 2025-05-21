package kr.hhplus.be.interfaces;


import kr.hhplus.be.domain.product.Product;
import kr.hhplus.be.domain.product.ProductService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

    @PostMapping("/update/{productId}")
    public boolean updateProduct(@RequestBody Long productId, @RequestBody Long salesAmount) {
        productService.updateProductInfo(productId, salesAmount);
        return true;
    }
    @GetMapping("/bestsellers/{date}")
    public Set<String> getBestSellersByDate(@PathVariable LocalDate date, int topN) {
        return productService.getTopProductsByDate(date, topN);
    }
    @GetMapping("/bestsellers/{days}/{topN}")
    public Set<String> getBestSellersForDays(@PathVariable int days, @PathVariable int topN) {
        return productService.getTopProductForDays(days, topN);
    }
}
