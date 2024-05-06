package africa.semicolon.data.model;

import io.jsonwebtoken.lang.Collections;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class User  {
    private String username;
    private String email;
    private String password;
    private UserType userType;
    @Id
    private String userId;
    private boolean isLoggedIn = false;
    @DBRef
    private List<Quiz> quiz;

}

