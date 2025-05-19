package kr.hhplus.be.domain.product;


import kr.hhplus.be.application.OrderItemRequest;
import kr.hhplus.be.domain.order.entity.OrderItem;
import kr.hhplus.be.domain.product.repository.ProductCacheRepository;
import kr.hhplus.be.domain.product.repository.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class ProductService {
    private final String SALES_KEY = "product:sales";

    private final ProductRepository productRepository;
    private final ProductCacheRepository productCacheRepository;

    public ProductService(ProductRepository productRepository, ProductCacheRepository productCacheRepository) {
        this.productRepository = productRepository;
        this.productCacheRepository = productCacheRepository;
    }

    @Transactional
    public Product registerProduct(String name, BigDecimal price, Long stock) {
        Product product = new Product(name, price, stock);

        return productRepository.save(product);
    }

    @Cacheable(value = "products")
    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getProduct(String name) {
        return productRepository.findByName(name);
    }

    public void checkStock(List<OrderItemRequest> orderItemRequest) {
        for (OrderItemRequest itemRequest : orderItemRequest) {
            Product product = itemRequest.getProduct();
            if (product == null) {
                throw new IllegalArgumentException("상품이 존재하지 않습니다.");
            }
            Integer quantity = itemRequest.getQuantity();
            if (!product.check(quantity)) {
                throw new RuntimeException("재고가 부족합니다.");
            }
        }
    }

    public void decreaseStock(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            Product product = orderItem.getProduct();
            Integer quantity = orderItem.getQuantity();

            product.decreaseStock(quantity);
        }
    }

    @Transactional
    public void decreaseProductStock(Product product, Integer quantity) {

        product.decreaseStock(quantity);
    }

    public void updateProductInfo(Long productId, Long salesAmount) {
        // Redis에 상품 정보 업데이트
        // 오늘의 키
        String todaySalesKey = SALES_KEY + LocalDate.now();

        // 상품 정보가 Redis에 존재하지 않는 경우
        // ZSet에 상품 ID와 판매량을 추가
        boolean isAdded = productCacheRepository.saveIfAbsent(todaySalesKey, productId.toString(), salesAmount);
        if (!isAdded) {
            // 상품 정보가 Redis에 존재하는 경우
            // ZSet의 판매량을 증가
            productCacheRepository.increment(todaySalesKey, productId.toString(), salesAmount);
        }
    }
    public Set<String> getTopProductsByDate(LocalDate date, int topN) {
        // ZSet에서 판매량이 가장 높은 상품 ID를 가져옴
        String todaySalesKey = SALES_KEY + date;
        return productCacheRepository.getBestSellers(todaySalesKey, 0, topN - 1);
    }

    public Set<String> getTopProductForDays(int days, int topN) {
        List<String> topSalesKey = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            String dateKey = SALES_KEY + LocalDate.now().minusDays(i);
            topSalesKey.add(dateKey);
        }

        String tempKey = SALES_KEY + "temp";
        productCacheRepository.unionAndStore(topSalesKey.get(0), topSalesKey.subList(1, topSalesKey.size()), tempKey);

        Set<String> topProductsForDays = productCacheRepository.getBestSellers(tempKey, 0, topN - 1);
        // 임시 키 삭제
        productCacheRepository.delete(tempKey);

        return topProductsForDays;
    }
}