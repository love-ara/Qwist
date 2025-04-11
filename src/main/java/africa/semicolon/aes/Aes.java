package africa.semicolon.aes;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Aes {
    public static String aesKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] secretKeyEncoded = secretKey.getEncoded();
        return Base64.getEncoder().encodeToString(secretKeyEncoded);
    }

    public static String aesIV() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);
        return Base64.getEncoder().encodeToString(iv);
    }


    public static String aesEncryption(byte[] dataToEncrypt, String key, String  ivString) throws Exception {
        byte[] encryptedData;
        try {
            // Here is  creating a Cipher block with the encryption type
            Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // here is  decoding the key passed in, because it's a base64 encoded string
            byte[] encryptionKey = Base64.getDecoder().decode(key);
            SecretKeySpec secretKeySpec = new SecretKeySpec(encryptionKey, "AES");

            // here is  decoding the iv passed in, because it's a base64 encoded string
            byte[] iv = Base64.getDecoder().decode(ivString);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);


            // here is initializing the encryption mode using the key and the iv for uniqueness
            aesCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            // Here is encrypting the data using the initialized mode
            encryptedData = aesCipher.doFinal(dataToEncrypt);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Error Encrypting data \n");
        }

        // Here is ensuring that the data was encrypted
        if(encryptedData == null) throw new Exception("Error Encrypting data : " + "encrypted data is null");

        // here is the data been returned as a base64 encode string
        return Base64.getEncoder().encodeToString(encryptedData);
    }



    public static String aesDecryption(String dataToDecrypt, String key, String ivString) throws Exception {
        try {
            // Here is converting the dataToDecrypt back to a byte array
            byte[] encryptedData = Base64.getDecoder().decode(dataToDecrypt);

            // Here is  creating a Cipher block with the encryption type
            Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // here is  decoding the key passed in, because it's a base64 encoded string
            byte[] encryptionKey = Base64.getDecoder().decode(key);
            SecretKeySpec secretKeySpec = new SecretKeySpec(encryptionKey, "AES");

            // here is  decoding the iv passed in, because it's a base64 encoded string
            byte[] iv = Base64.getDecoder().decode(ivString);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            // here is initializing the decryption mode using the key and the iv for uniqueness
            aesCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            // Here is decrypting the data using the initialized mode
            byte[] bytes = aesCipher.doFinal(encryptedData);

            // Here is the decrypted data returned as a string, this can be mapped to the object expected
            return new String(bytes);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error decrypting data");
        }

    }

}
