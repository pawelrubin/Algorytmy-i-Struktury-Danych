package com.pawelrubin.structures;

public class Validator<T> {

    public T fix(T element) {
        if (element instanceof String) {
            if (element == "") return null;
            return (T) stringFix((String) element);
        } else {
            return element;
        }
    }

    private String stringFix(String element) {
        if (element.equals("")) return element;
        if (invalidChar((element).charAt(0))) {
            return stringFix(element.substring(1));
        }
        if (invalidChar(element.charAt((element.length() - 1)))) {
            return stringFix(element.substring(0, (element.length() - 1)));
        }
        return element;
    }

    private boolean invalidChar(char c) {
        return (c < 65 || c > 90) && (c < 97 || c > 122);
    }
}
