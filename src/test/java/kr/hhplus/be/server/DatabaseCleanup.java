package kr.hhplus.be.server;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseCleanup {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void truncateAllTables() {

        entityManager.createNativeQuery("TRUNCATE TABLE user").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE point").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE point_history").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE coupon").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE user_coupon").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE product").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE orders").executeUpdate();
    }
}