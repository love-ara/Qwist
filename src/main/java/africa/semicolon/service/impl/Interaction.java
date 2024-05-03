package africa.semicolon.service.impl;

import africa.semicolon.dto.request.CreateQuizRequest;
import africa.semicolon.dto.request.RegisterUserRequest;
import africa.semicolon.dto.request.UserLoginRequest;
import africa.semicolon.dto.response.CreateQuizResponse;
import africa.semicolon.dto.response.RegisterUserResponse;
import africa.semicolon.dto.response.UserLoginResponse;
import africa.semicolon.service.services.QuizService;
import africa.semicolon.service.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class Interaction{
    private QuizService quizService;
    private UserService userService;

    private RegisterUserResponse signUp(RegisterUserRequest registerUserRequest){
        return userService.registerUser(registerUserRequest);
    }

    private UserLoginResponse login(UserLoginRequest userLoginRequest){
        return userService.login(userLoginRequest);
    }
    private CreateQuizResponse create(UserLoginRequest loginRequest, CreateQuizRequest createQuizRequest){
            userService.login(loginRequest);
            return quizService.createQuiz(createQuizRequest);

        }


}
