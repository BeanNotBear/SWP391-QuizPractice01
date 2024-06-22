
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.security;

import java.security.SecureRandom;

/**
 *
 * @author nghin
 */
public class CodeVerify2 {

    // Define the length of the verification code
    private static final int CODE_LENGTH = 30;

    // Characters allowed in the verification code
    private static final String ALLOWED_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    // SecureRandom instance for generating random numbers
    private static final SecureRandom RANDOM = new SecureRandom();

    // Method to generate a random verification code
    public static String generateVerificationCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = RANDOM.nextInt(ALLOWED_CHARACTERS.length());
            sb.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    public static String generateVerificationCodeOTP() {
        int number = RANDOM.nextInt(999999);
        return String.format("%06d", number);

    }
}