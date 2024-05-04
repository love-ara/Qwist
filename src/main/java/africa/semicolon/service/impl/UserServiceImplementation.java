package africa.semicolon.service.impl;

import africa.semicolon.data.model.User;
import africa.semicolon.data.repository.QuizRepository;
import africa.semicolon.data.repository.UserRepository;
import africa.semicolon.dto.request.*;
import africa.semicolon.dto.response.*;
import africa.semicolon.service.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static africa.semicolon.util.Map.*;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService, UserDetailsService {
    private UserRepository userRepository;
    private QuizRepository quizService;

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
    private void isUserLoggedIn(String username) {
       var loggedInUser = findUser(username);
       if(!loggedInUser.isLoggedIn()){
           throw new IllegalArgumentException("User is not logged in");
       }
    }

    public UserLogoutResponse logout(UserLogoutRequest userLogoutRequest){
        User user = findUser(userLogoutRequest.getUsername());
        user.setLoggedIn(false);
        userRepository.save(user);
        return logoutMap(userLogoutRequest, user);
    }

    @Override
    public SelectQuizResponse selectQuiz(SelectQuizRequest selectQuizRequest) {
        //registered, logged-in, not asked too many details
        findUser(selectQuizRequest.getUsername());
        isUserLoggedIn(selectQuizRequest.getUsername());

        var quiz = quizService.findById(selectQuizRequest.getQuizId()).orElseThrow(()->new IllegalArgumentException("Quiz not found"));
        SelectQuizResponse response = new SelectQuizResponse();
        response.setQuizId(quiz.getQuizId());
        response.setQuizTitle(selectQuizRequest.getQuizTitle());
        response.setQuestion(List.of(quiz.getQuestions()).toString());
        return response;
    }

    @Override
    public TakeQuizResponse takeQuiz(TakeQuizRequest takeQuizRequest) {
        findUser(takeQuizRequest.getUsername());
        isUserLoggedIn(takeQuizRequest.getQuizId());

        SelectQuizRequest request = new SelectQuizRequest();
        request.setQuizId(takeQuizRequest.getQuizId());
        request.setQuizTitle(takeQuizRequest.getQuizTitle());
        request.setUsername(takeQuizRequest.getUsername());
        var response = selectQuiz(request);



        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username.toLowerCase());
        if(user == null){
            throw new IllegalArgumentException("Username not found");
        }
        return user;
    }
}

