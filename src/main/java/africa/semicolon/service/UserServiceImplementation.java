package africa.semicolon.service;

import africa.semicolon.data.model.User;
import africa.semicolon.data.repository.UserRepository;
import africa.semicolon.dto.request.RegisterUserRequest;
import africa.semicolon.dto.request.SelectQuizRequest;
import africa.semicolon.dto.request.UserLoginRequest;
import africa.semicolon.dto.request.UserLogoutRequest;
import africa.semicolon.dto.response.RegisterUserResponse;
import africa.semicolon.dto.response.SelectQuizResponse;
import africa.semicolon.dto.response.UserLoginResponse;
import africa.semicolon.dto.response.UserLogoutResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static africa.semicolon.util.Map.*;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService{
    private UserRepository userRepository;

    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest) {
        validateRegisteredUser(registerUserRequest.getUsername());
        User user = registerMap(registerUserRequest);
        User registeredUser = userRepository.save(user);
        return registerUserResponse(registeredUser);
    }

    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        User user = findUser(userLoginRequest.getUsername());
        authenticate(userLoginRequest.getPassword(), user);
        user.setLoggedIn(true);
        userRepository.save(user);
        return loginUserResponse(user);
    }

    private static void authenticate(String password, User user) {
        if(!user.getPassword().equals(password)){
           throw new IllegalArgumentException("Invalid username or password");
        }
    }

    private void validateRegisteredUser(String username) {
        if(userRepository.existsByUsername(username.toLowerCase())){
            throw new IllegalArgumentException("Username is already in exist");
        }
    }

    private User findUser(String username){
        User user = userRepository.findByUsername(username.toLowerCase());
        if(user == null){
            throw new IllegalArgumentException("Username not found");
        }
        return user;
    }

    public UserLogoutResponse logout(UserLogoutRequest userLogoutRequest){
        User user = findUser(userLogoutRequest.getUsername());
        user.setLoggedIn(false);
        userRepository.save(user);
        return logoutMap(userLogoutRequest, user);
    }

    @Override
    public SelectQuizResponse selectQuiz(SelectQuizRequest selectQuizRequest) {
        return null;
    }
//
//    public boolean authorizeUser(UserLoginRequest userLoginRequest) {
//        String userId; String action;
//        return findUser(userLoginRequest);
//    }
}

