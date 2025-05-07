package kr.hhplus.be.infrastructure.product;

import jakarta.persistence.NoResultException;
import kr.hhplus.be.domain.product.Product;
import kr.hhplus.be.domain.product.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductJpaRepository productJpaRepository;


    public ProductRepositoryImpl(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll();
    }
    @Override
    public Product findById(Long id) {
        return productJpaRepository.findById(id).orElseThrow(() -> new NoResultException("Product not found with id: " + id));
    }
    @Override
    public Product findByName(String name) {
        return productJpaRepository.findByName(name).orElseThrow(() -> new NoResultException("Product not found with name: " + name));
    }
    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Optional<Product> findByPessimisticLock(Long id) {
        return productJpaRepository.findByPessimisticLock(id);
    }
}
