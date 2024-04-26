package africa.semicolon.dto.response;

import lombok.Data;

@Data
public class UserLogoutResponse {
    private String username;
    private boolean loggedIn = false;
}
