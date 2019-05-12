package com.pawelrubin.structures;

public class StringValidator {
    public String fixString(String string) {
        if (invalidChar(string.charAt(0))) {
            return fixString(string.substring(1));
        }
        if (invalidChar(string.charAt(string.length() - 1))) {
            return fixString(string.substring(0, string.length() - 1));
        }
        return string;
    }

    private boolean invalidChar(char c) {
        return (c < 65 || c > 90) && (c < 97 || c > 122);
    }
}
