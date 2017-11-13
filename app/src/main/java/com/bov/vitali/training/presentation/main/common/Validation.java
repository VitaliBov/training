package com.bov.vitali.training.presentation.main.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Validation {
    private static final String NAME_PATTERN = "^[a-zA-Z\\\\s]*$";

    private Validation() {
    }

    static boolean isNameValid(String name) {
        return init(name, NAME_PATTERN) & (name.length() > 1);
    }

    private static boolean init(String text, String value) {
        Pattern pattern = Pattern.compile(value, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
}