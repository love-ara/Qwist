package africa.semicolon.controller;

import africa.semicolon.dto.request.RegisterUserRequest;
import africa.semicolon.dto.request.UserLoginRequest;
import africa.semicolon.dto.request.UserLogoutRequest;
import africa.semicolon.dto.response.ApiResponse;
import africa.semicolon.service.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/Register")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest userRequest){
        try{
            var user = userService.registerUser(userRequest);
            return new ResponseEntity<>(new ApiResponse(true, user), OK);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest){
        try{
            var user = userService.login(userLoginRequest);
            return new ResponseEntity<>(new ApiResponse(true, user), OK);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), OK);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody UserLogoutRequest userLogoutRequest){
        try{
            var user = userService.logout(userLogoutRequest);
            return new ResponseEntity<>(new ApiResponse(true, user), OK);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), OK);
        }
    }


}
