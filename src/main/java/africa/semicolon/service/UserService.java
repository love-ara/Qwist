package africa.semicolon.service;

import africa.semicolon.dto.request.*;
import africa.semicolon.dto.response.*;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest);
    UserLoginResponse login(UserLoginRequest userLoginRequest);
    UserLogoutResponse logout(UserLogoutRequest userLogoutRequest);
    SelectQuizResponse selectQuiz(SelectQuizRequest selectQuizRequest);
    TakeQuizResponse takeQuiz(TakeQuizRequest takeQuizRequest);
}
