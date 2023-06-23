package kr.co.programmers.springbootbasic.util;

public class VoucherUtils {
    private VoucherUtils() {
    }

    public static int parseStringToInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("올바른 숫자입력이 아닙니다.");
        }
    }

    public static long parseStringToLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("올바른 숫자입력이 아닙니다.");
        }
    }
}
