package africa.semicolon.aes;

import com.fasterxml.jackson.databind.ObjectMapper;

import static africa.semicolon.aes.Aes.*;

public class AesTestMain {

    public static void main(String[] args) throws Exception {
        Card card = new Card();
        card.setSecretKey("123");
        card.setScheme("https");
        card.setCardNumber("23565");
        card.setCvv("123436");


        ObjectMapper objectMapper = new ObjectMapper();

        // this convert the Object toEncrypt into a String so you will have a String json object
        String dataToEncrypt = objectMapper.writeValueAsString(card);

        // Here is the Key used for encryption
        String key = aesKey();
        // and Here is the iv used for encryption
        String iv = aesIV();

        // Here is the encryption method been called
        // It takes in the ObjectToEncrypt as a byte array
        String encryptedData = aesEncryption(dataToEncrypt.getBytes(), key, iv);

        System.out.println(encryptedData);

        // Here is the decryption method been called
        // It takes in  the encryptedData, the key used in encrypting and the iv used in encrypting as a String, and return a String of the decrypted String
        String  decryptedData = aesDecryption(encryptedData, key, iv);


        // Here is the decrypted data been mapped to the Object that is expected to be decrypted
        Card cardDecrypted = objectMapper.readValue(decryptedData, Card.class);

        // Here is me logging the decrypted data
        System.out.println("The card is: " + cardDecrypted);
    }
}
