package africa.semicolon.service;


import africa.semicolon.data.model.Question;
import africa.semicolon.data.repository.UserRepository;
import africa.semicolon.dto.request.*;
import africa.semicolon.service.services.QuizService;
import africa.semicolon.service.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTest {
    private final UserService userService;
    private final UserRepository userRepository;
    private final QuizService quizService;
    private RegisterUserRequest registerUserRequest;
    private UserLoginRequest userLoginRequest;
    private UserLogoutRequest userLogoutRequest;
    private CreateQuizRequest createQuizRequest;

    @Autowired
    public UserServiceTest(UserService userService, UserRepository userRepository, QuizService quizService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.quizService = quizService;
    }

    @BeforeEach
    public void setUp(){
        userRepository.deleteAll();


        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setFirstname("firstname");
        registerUserRequest.setLastname("lastname");
        registerUserRequest.setUsername("username");
        registerUserRequest.setPassword("password");
        registerUserRequest.setEmail("email@gmail.com");

        userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUsername("username");
        userLoginRequest.setPassword("password");

        userLogoutRequest = new UserLogoutRequest();
        userLogoutRequest.setUsername("username");

        createQuizRequest = new CreateQuizRequest();
        createQuizRequest.setQuizTitle("Quiz Name");

        CreateQuestionRequest createQuestionRequest = new CreateQuestionRequest();
        createQuestionRequest.setQuestionContent("Question Content");
        OptionRequest optionRequest = new OptionRequest();
        optionRequest.setOptionContent("Option Content");

        createQuestionRequest.setOption(List.of(optionRequest));
        createQuestionRequest.setAnswer("answer");
        createQuizRequest.setCreateQuestionRequest(List.of(createQuestionRequest));


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
        registerUserAgain.setFirstname("firstname");
        registerUserAgain.setLastname("lastname");
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

    @Test
    public void registeredUserCanSelectQuizTest(){
        userService.registerUser(registerUserRequest);
        userService.login(userLoginRequest);
        assertThat(userRepository.count(), is(1L));
        assertThat(userRepository.findAll().getFirst().isLoggedIn(), is(true));


        var quiz = quizService.createQuiz(createQuizRequest);


        SelectQuizRequest selectQuizRequest = new SelectQuizRequest();
        selectQuizRequest.setQuizId(quiz.getQuizId());
        selectQuizRequest.setUsername(userLoginRequest.getUsername());
        //selectQuizRequest.setQuizCategory("quizCategory");

        userService.selectQuiz(selectQuizRequest);

        List<Question> quizQuestions = quizService.getQuizQuestions(selectQuizRequest.getQuizId());

        assertThat(quizQuestions, not(empty()));

    }

    @Test
    public void registeredUserCanTakeQuizTest(){
        userService.registerUser(registerUserRequest);
        userService.login(userLoginRequest);
        assertThat(userRepository.count(), is(1L));
        assertThat(userRepository.findAll().getFirst().isLoggedIn(), is(true));

        var quiz = quizService.createQuiz(createQuizRequest);

        SelectQuizRequest selectQuizRequest = new SelectQuizRequest();
        selectQuizRequest.setQuizId(quiz.getQuizId());
        selectQuizRequest.setUsername(userLoginRequest.getUsername());
        userService.selectQuiz(selectQuizRequest);

        TakeQuizRequest takeQuizRequest = new TakeQuizRequest();
        takeQuizRequest.setUsername(userLoginRequest.getUsername());
        takeQuizRequest.setQuizId(quiz.getQuizId());
        takeQuizRequest.setQuizName(quiz.getQuizName());
        takeQuizRequest.setAnswer(List.of("answer"));

        userService.takeQuiz(takeQuizRequest);
    }



}
