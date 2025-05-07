package kr.hhplus.be.test.user;

import kr.hhplus.be.domain.user.User;
import kr.hhplus.be.domain.user.UserRepository;
import kr.hhplus.be.domain.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    private String userName = "Jiwoo";

    //TODO: 사용자를 생성, 등록할 수 있다.
    @Test
    void 사용자_생성_등록() {
        //given
        User fakeUser = new User(userName);
        when(userRepository.save(any(User.class)))
                .thenReturn(fakeUser);
        //when
        User user = userService.registerUser(userName);

        //then
        Assertions.assertNotNull(user);
        Assertions.assertEquals(user, fakeUser);
        verify(userRepository).save(any(User.class));

    }

}
