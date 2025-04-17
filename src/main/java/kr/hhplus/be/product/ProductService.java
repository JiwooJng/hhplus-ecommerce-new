package kr.hhplus.be.product;


import kr.hhplus.be.product.entity.Product;
import kr.hhplus.be.product.entity.ProductStock;
import kr.hhplus.be.product.repository.ProductRepository;
import kr.hhplus.be.product.repository.ProductStockRepository;

import java.math.BigDecimal;
import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;
    private final ProductStockRepository productStockRepository;

    public ProductService(ProductRepository productRepository, ProductStockRepository productStockRepository) {
        this.productRepository = productRepository;
        this.productStockRepository = productStockRepository;
    }

    public Product registerProduct(String name, BigDecimal price, int quantity) {
        Product product = new Product(name, price);
        ProductStock productStock = new ProductStock(product, quantity);
        product.setStock(productStock);

        return productRepository.save(product);
    }
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getProduct(String name) {
        return productRepository.findByName(name);
    }
}