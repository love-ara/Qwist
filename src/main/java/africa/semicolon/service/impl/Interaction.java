package africa.semicolon.service.impl;

import africa.semicolon.dto.request.*;
import africa.semicolon.dto.response.*;
import africa.semicolon.service.services.QuizService;
import africa.semicolon.service.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class Interaction{
    private QuizService quizService;
    private UserService userService;

    public RegisterUserResponse signUp(RegisterUserRequest registerUserRequest){
        return userService.registerUser(registerUserRequest);
    }

    public UserLoginResponse login(UserLoginRequest userLoginRequest){
        return userService.login(userLoginRequest);
    }
    public CreateQuizResponse create(UserLoginRequest loginRequest, CreateQuizRequest createQuizRequest){
            userService.login(loginRequest);
            return quizService.createQuiz(createQuizRequest);
    }

    public DeleteQuizResponse delete(DeleteQuizRequest deleteQuizRequest){
      return quizService.deleteQuiz(deleteQuizRequest);
    }

    public UpdateQuizResponse update(UpdateQuizRequest updateQuizRequest){
        return quizService.updateQuiz(updateQuizRequest);
    }
    public UserLogoutResponse logout(UserLogoutRequest userLogoutRequest){
        return userService.logout(userLogoutRequest);
    }

}
