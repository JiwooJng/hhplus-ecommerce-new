package kr.hhplus.be.domain.user;

public interface UserRepository {
    User findById(Long userId);

    User save(User user);

}
