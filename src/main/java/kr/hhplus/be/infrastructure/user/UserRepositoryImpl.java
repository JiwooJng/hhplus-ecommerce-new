package kr.hhplus.be.infrastructure.user;

import kr.hhplus.be.domain.user.User;
import kr.hhplus.be.domain.user.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User findById(Long userId) {
        return userJpaRepository.findById(userId)
                .orElseThrow(null);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }
}
