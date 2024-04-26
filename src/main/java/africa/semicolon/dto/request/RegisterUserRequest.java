package africa.semicolon.dto.request;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
}
