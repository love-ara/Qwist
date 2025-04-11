package africa.semicolon.aes;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Card {
    private String cardNumber;
    private String cvv;
    private String expiryDate;
    private String issuer;
    private String Scheme;
    private String SecretKey;
}
