package me.akshawop.devjournalapp.util;

import java.util.Optional;

public class OtherUtils {
    
    public static Optional<Integer> tryParseInt(String str) {
        try {
            return Optional.of(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
