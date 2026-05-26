package com.bfhl.api;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    private static final String USER_ID    = "taha_rangwala_03072005";
    private static final String EMAIL      = "taharangwala230941@acropolis.in";
    private static final String ROLL_NUMBER = "0827CD231069";

    @Override
    public BfhlResponse processData(List<String> data) {
        List<String> oddNumbers       = new ArrayList<>();
        List<String> evenNumbers      = new ArrayList<>();
        List<String> alphabets        = new ArrayList<>();
        List<String> specialChars     = new ArrayList<>();
        long sumVal                   = 0;

        for (String item : data) {
            if (isNumber(item)) {
                long num = Long.parseLong(item);
                sumVal += num;
                if (num % 2 == 0) {
                    evenNumbers.add(item);
                } else {
                    oddNumbers.add(item);
                }
            } else if (isAlphabet(item)) {
                alphabets.add(item.toUpperCase());
            } else {
                specialChars.add(item);
            }
        }

        String concatStr = buildConcatString(alphabets);

        return BfhlResponse.builder()
                .isSuccess(true)
                .userId(USER_ID)
                .email(EMAIL)
                .rollNumber(ROLL_NUMBER)
                .oddNumbers(oddNumbers)
                .evenNumbers(evenNumbers)
                .alphabets(alphabets)
                .specialCharacters(specialChars)
                .sum(String.valueOf(sumVal))
                .concatString(concatStr)
                .build();
    }

    private boolean isNumber(String s) {
        if (s == null || s.isEmpty()) return false;
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    private boolean isAlphabet(String s) {
        if (s == null || s.isEmpty()) return false;
        for (char c : s.toCharArray()) {
            if (!Character.isLetter(c)) return false;
        }
        return true;
    }

    /**
     * Collect ALL individual alpha chars from uppercase-converted alphabets list,
     * reverse the list, then apply alternating caps starting with UPPER at index 0.
     */
    private String buildConcatString(List<String> alphabets) {
        List<Character> allChars = new ArrayList<>();
        for (String word : alphabets) {
            for (char c : word.toCharArray()) {
                allChars.add(c);
            }
        }

        // Reverse
        List<Character> reversed = new ArrayList<>();
        for (int i = allChars.size() - 1; i >= 0; i--) {
            reversed.add(allChars.get(i));
        }

        // Alternating caps: index 0 = upper, index 1 = lower, ...
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < reversed.size(); i++) {
            char c = reversed.get(i);
            if (i % 2 == 0) {
                sb.append(Character.toUpperCase(c));
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }
}
