package com.legalsight.speechmanagementapi.exception;

import java.util.HashMap;
import java.util.Map;

public class ErrorContants {
    // Map of all error codes and their default messages
    public static final Map<String, String> ERRORS;


    static {
        ERRORS = new HashMap<>();

        ERRORS.put("SPH001", "Speech not found.");
        // Validation errors;
        ERRORS.put("SPH002", "Author is required.");
        ERRORS.put("SPH003", "Subject is required.");
        ERRORS.put("SPH004", "Speech body is required.");
        ERRORS.put("SPH005", "The speech date format is invalid. Please use yyyy-MM-dd.");

        ERRORS.put("SPH999", "An unexpected error occurred. Please contact support.");

    }

    // Optional utility to get message by code
    public static String getMessage(String code) {
        return ERRORS.getOrDefault(code, "Unknown error");
    }
}
