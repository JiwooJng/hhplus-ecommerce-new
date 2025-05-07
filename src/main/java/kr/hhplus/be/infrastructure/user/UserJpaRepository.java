package kr.hhplus.be.infrastructure.user;

import kr.hhplus.be.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long userId);
}
