package kr.hhplus.be.domain.product;


import kr.hhplus.be.application.OrderItemRequest;
import kr.hhplus.be.domain.order.entity.OrderItem;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product registerProduct(String name, BigDecimal price, Long stock) {
        Product product = new Product(name, price, stock);

        return productRepository.save(product);
    }

    @Cacheable(value = "products", key = "#productId")
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
}