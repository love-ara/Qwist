package africa.semicolon.util;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class GamePinGenerator {

    private static final int PIN_LENGTH = 6; // Length of the game PIN
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // Characters to use for generating the PIN
    private static final SecureRandom random = new SecureRandom();

    // Use a set to store existing game PINs to check for uniqueness
    private static final Set<String> existingPins = new HashSet<>();

    public static String generateGamePin() {
        StringBuilder pin = new StringBuilder(PIN_LENGTH);

        // Generate a random game PIN
        for (int i = 0; i < PIN_LENGTH; i++) {
            pin.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        String gamePin = pin.toString();

        // Check if the game PIN is unique
        while (existingPins.contains(gamePin)) {
            gamePin = generateGamePin();
        }

        // Add the game PIN to the set of existing game PINs
        existingPins.add(gamePin);

        return gamePin;
    }
}
