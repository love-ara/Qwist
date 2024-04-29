package africa.semicolon.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Users")
public class User {
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
    @Id
    private String userId;
    private boolean isLoggedIn = false;
}

