package org.weekly.weekly.ui.reader;

public class ReadException {

    public static void isEmpty(String userInput) {
        if (userInput == null || userInput.isBlank()) {
            throw new RuntimeException("사용자가 아무 값도 입력하지 않았습니다.");
        }
    }

    public static void notInputFormat(String userInput) {
        if (containsIntOrBlank(userInput)) {
            throw new RuntimeException("입력 형식에 맞지 않습니다.");
        }
    }

    private static boolean containsIntOrBlank(String userInput) {
        return userInput.chars().anyMatch(value -> value==' ' || Character.isDigit(value));
    }
}
