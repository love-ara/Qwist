package africa.semicolon.service;

import africa.semicolon.dto.request.RegisterUserRequest;
import africa.semicolon.dto.request.UserLoginRequest;
import africa.semicolon.dto.request.UserLogoutRequest;
import africa.semicolon.dto.response.RegisterUserResponse;
import africa.semicolon.dto.response.UserLoginResponse;
import africa.semicolon.dto.response.UserLogoutResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest);
    UserLoginResponse login(UserLoginRequest userLoginRequest);
    UserLogoutResponse logout(UserLogoutRequest userLogoutRequest);
}
