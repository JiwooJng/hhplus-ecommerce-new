package kr.hhplus.be.user;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String name) {
        User user = new User(name);

        return userRepository.save(user);
    }
    public User findById(Long userId) {
        return userRepository.findById(userId);
    }



}
