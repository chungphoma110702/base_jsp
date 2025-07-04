package com.example.base_jsp.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

@Component
public class StringUtil {
    public static boolean isNullOrEmpty(String s) {
        return (s == null) || "".equalsIgnoreCase(s.trim());
    }

    public static String generateRandomOrderCode(Long garageId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = dateFormat.format(new Date());

        Random random = new Random();
        StringBuilder randomChars = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            char randomChar = (char) (random.nextInt(26) + 'a'); // Generate a random lowercase letter
            randomChars.append(randomChar);
        }
        return new StringBuilder().append(formattedDate).append(randomChars.toString()).append("gr").append(garageId.toString()).toString();
    }

    public static String generateRandomCode(Long garageId, String signatureText) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = dateFormat.format(new Date());

        Random random = new Random();
        StringBuilder randomChars = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            char randomChar = (char) (random.nextInt(26) + 'a'); // Generate a random lowercase letter
            randomChars.append(randomChar);
        }
        return new StringBuilder().append(formattedDate).append(randomChars).append(signatureText).append(garageId.toString()).toString();
    }

    public static String generateNumericString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be a positive integer");
        }

        Random random = new Random();
        StringBuilder numericString = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // Generates a random number between 0 and 9
            numericString.append(digit);
        }

        return numericString.toString();
    }

    public static String getNextValue(String currentValue) {
        int currentNumber = Integer.parseInt(currentValue);
        int nextNumber = currentNumber + 1;
        return String.format("%02d", nextNumber);
    }

    public static String getStringLike(String content) {
        if (isNullOrEmpty(content)) {
            return "%%";
        }
        return "%" + TextUtil.normalize(content).toLowerCase() + "%";
    }

    public static boolean validateRegex(String email, String stringPattern) {
        Pattern pattern = Pattern.compile(stringPattern);
        return pattern.matcher(email).matches();
    }
}
