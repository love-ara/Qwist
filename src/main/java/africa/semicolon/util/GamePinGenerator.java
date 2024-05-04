package africa.semicolon.util;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class GamePinGenerator {

    private static final int PIN_LENGTH = 6; // Length of the game PIN
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom random = new SecureRandom();


    private static final Set<String> existingPins = new HashSet<>();

    public static String generateGamePin() {
        StringBuilder pin = new StringBuilder(PIN_LENGTH);

        for (int i = 0; i < PIN_LENGTH; i++) {
            pin.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        String gamePin = pin.toString();

        while (existingPins.contains(gamePin)) {
            gamePin = generateGamePin();
        }

        existingPins.add(gamePin);

        return gamePin;
    }
}
