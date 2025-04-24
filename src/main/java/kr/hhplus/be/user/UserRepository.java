package kr.hhplus.be.user;

public interface UserRepository {
    User findById(Long userId);

    User save(User user);

}
