package org.prgrms.kdt.util;

public class Utility {

    private Utility() {
    }

    public static boolean isNumber(String inputString) {
        return inputString.chars()
                .filter(Character::isDigit)
                .count() == inputString.length();
    }

    public static int toInt(String inputString) {
        if (!isNumber(inputString)) throw new IllegalArgumentException("숫자를 입력하세요.");
        return Integer.parseInt(inputString);
    }
}
