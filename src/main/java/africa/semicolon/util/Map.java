package africa.semicolon.util;

import africa.semicolon.data.model.User;
import africa.semicolon.dto.request.RegisterUserRequest;
import africa.semicolon.dto.request.UserLoginRequest;
import africa.semicolon.dto.request.UserLogoutRequest;
import africa.semicolon.dto.response.RegisterUserResponse;
import africa.semicolon.dto.response.UserLoginResponse;
import africa.semicolon.dto.response.UserLogoutResponse;

public class Map {

    public static User registerMap(RegisterUserRequest registerUserRequest){
        User user = new User();
        user.setUsername(registerUserRequest.getUsername());
        user.setPassword(registerUserRequest.getPassword());
        user.setEmail(registerUserRequest.getEmail());
        return user;
    }

    public static RegisterUserResponse registerUserResponse(User user){
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setId(user.getUserId());
        registerUserResponse.setUsername(user.getUsername());
        return registerUserResponse;
    }

    public static UserLogoutResponse logoutMap(UserLogoutRequest logoutRequest, User user){
        UserLogoutResponse logoutResponse = new UserLogoutResponse();
        logoutResponse.setUsername(logoutRequest.getUsername());
        logoutResponse.setLoggedIn(user.isLoggedIn());
        return logoutResponse;
    }

    public static UserLoginResponse loginUserResponse(User user){
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        userLoginResponse.setUserId(user.getUserId());
        userLoginResponse.setUsername(user.getUsername());
        return userLoginResponse;
    }
}
