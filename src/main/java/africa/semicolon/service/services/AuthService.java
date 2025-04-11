package africa.semicolon.service.services;

import africa.semicolon.dto.request.RegisterUserRequest;
import africa.semicolon.dto.request.UserLoginRequest;
import africa.semicolon.dto.request.UserLogoutRequest;
import africa.semicolon.dto.response.RegisterUserResponse;
import africa.semicolon.dto.response.UserLoginResponse;
import africa.semicolon.dto.response.UserLogoutResponse;

public interface AuthService {
    RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest);
    UserLoginResponse login(UserLoginRequest userLoginRequest);
    UserLogoutResponse logout(UserLogoutRequest userLogoutRequest);
}
