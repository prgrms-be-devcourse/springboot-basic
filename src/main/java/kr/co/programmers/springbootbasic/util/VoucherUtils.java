package kr.co.programmers.springbootbasic.util;

public class VoucherUtils {
    private VoucherUtils() {
    }

    public static int parseStringToInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("문자열을 숫자로 변환할 수 없습니다.");
        }
    }
}
