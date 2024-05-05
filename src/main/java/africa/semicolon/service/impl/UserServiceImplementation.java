package africa.semicolon.service.impl;

import africa.semicolon.data.model.User;
import africa.semicolon.data.repository.UserRepository;
import africa.semicolon.dto.request.*;
import africa.semicolon.dto.response.*;
import africa.semicolon.service.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



import static africa.semicolon.util.Map.*;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService, UserDetailsService {
    private UserRepository userRepository;
    private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest) {
        validateRegisteredUser(registerUserRequest.getUsername());
        if(!isEmailValid(registerUserRequest.getEmail())) {
            throw new IllegalArgumentException("Invalid email address");
        }


        User user = registerMap(registerUserRequest);
        User registeredUser = userRepository.save(user);
        return registerUserResponse(registeredUser);
    }

    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        User user = findUser(userLoginRequest.getUsername());
        authenticate(userLoginRequest.getPassword(), user);
        if(!user.isLoggedIn()){
            user.setLoggedIn(true);
        }else{
            throw new IllegalArgumentException("User already logged in");
        }
        userRepository.save(user);
        return loginUserResponse(user);
    }

    private static void authenticate(String password, User user) {
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(!passwordEncoder.matches(password, user.getPassword())){
           throw new IllegalArgumentException("Invalid username or password");
        }
    }

    private void validateRegisteredUser(String username) {
        if(userRepository.existsByUsername(username.toLowerCase())){
            throw new IllegalArgumentException("Username is already in exist");
        }
    }

    private User findUser(String usernameOrEmail){
        String input = usernameOrEmail.toLowerCase();

        if (isEmailValid(input)) {
            User user = userRepository.findByEmail(input);
            if (user == null) {
                throw new IllegalArgumentException("Email not found");
            }
            return user;
        }

        User user = userRepository.findByUsername(input);
        if (user == null) {
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
        isUserLoggedIn(userLogoutRequest.getUsername());
        user.setLoggedIn(false);
        userRepository.save(user);
        return logoutMap(userLogoutRequest, user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username.toLowerCase());
        if(user == null){
            throw new IllegalArgumentException("Username not found");
        }
        return user;
    }



    public static boolean isEmailValid(String email) {
        email = email.toLowerCase();
        String emailRegex = "\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return email.matches(emailRegex);
    }
}

