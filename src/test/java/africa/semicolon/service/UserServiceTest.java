package africa.semicolon.service;



import africa.semicolon.data.repository.UserRepository;
import africa.semicolon.dto.request.*;
import africa.semicolon.service.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTest {
    private final UserService userService;
    private final UserRepository userRepository;
    private RegisterUserRequest registerUserRequest;
    private UserLoginRequest userLoginRequest;
    private UserLogoutRequest userLogoutRequest;

    @Autowired
    public UserServiceTest(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setUp(){
        userRepository.deleteAll();


        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("username");
        registerUserRequest.setPassword("password");
        registerUserRequest.setEmail("email@gmail.com");

        userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUsername("username");
        userLoginRequest.setPassword("password");

        userLogoutRequest = new UserLogoutRequest();
        userLogoutRequest.setUsername("username");
    }

    @Test
    public void userCanBeRegisteredTest() {
        userService.registerUser(registerUserRequest);
        assertThat(userRepository.count(), is(1L));
    }

    @Test
    public void userCanNotBeRegisteredTest() {
        userService.registerUser(registerUserRequest);
        RegisterUserRequest registerUserAgain = new RegisterUserRequest();
        registerUserAgain.setUsername("username");
        registerUserAgain.setPassword("password");
        registerUserAgain.setEmail("email@gmail.com");

        assertThrows(IllegalArgumentException.class, ()-> userService.registerUser(registerUserAgain));
        assertThat(userRepository.count(), is(1L));
    }

    @Test
    public void userCanLoginTest(){
        userService.registerUser(registerUserRequest);
        userService.login(userLoginRequest);

        assertThat(userRepository.count(), is(1L));
        assertThat(userRepository.findAll().getFirst().isLoggedIn(), is(true));
    }

    @Test
    public void userCanLogoutTest(){
        userService.registerUser(registerUserRequest);
        userService.login(userLoginRequest);
        assertThat(userRepository.count(), is(1L));
        assertThat(userRepository.findAll().getFirst().isLoggedIn(), is(true));

        userService.logout(userLogoutRequest);
        assertThat(userRepository.findAll().getFirst().isLoggedIn(), is(false));
    }



}
