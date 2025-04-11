package africa.semicolon.util;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class AESKeyGenerator {

    public static String generateAndSaveNewKey() throws NoSuchAlgorithmException {

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey secretKey = keyGenerator.generateKey();

        byte[] secretKeyEncoded = secretKey.getEncoded();

        StringBuilder sb = new StringBuilder();
        for (byte b : secretKeyEncoded) {
            sb.append(String.format("%02X", b));
        }
        String generatedKey = sb.toString();

        saveKeyToFile(generatedKey);

        return generatedKey;
    }


    private static void saveKeyToFile(String base64Key) {
        try {
            String filePath = "AES_SECRET_KEY.env";
            String aesKey = "AES_SECRET_KEY=" + base64Key;
            java.nio.file.Files.write(java.nio.file.Paths.get(filePath), aesKey.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Error saving the AES key to a file", e);
        }
    }

    public static void main(String[] args) {
        try {
            String newKey = generateAndSaveNewKey();
            System.out.println("New AES Key (Base64): " + newKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
