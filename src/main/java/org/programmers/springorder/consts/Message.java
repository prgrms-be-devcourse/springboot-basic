package org.programmers.springorder.consts;

public class Message {

    public static final String MENU_SELECT_MESSAGE = """
            === Voucher Program ===
            1. Exit the program.
            2. Create a new voucher.
            3. List all vouchers.
            """;

    public static final String VOUCHER_SELECT_MESSAGE = """
            Q. 원하는 바우처 타입을 입력해주세요.
            1. Fixed Amount Voucher
            2. Percent Discount Voucher
            """;

    public static final String INPUT_DISCOUNT_VALUE_MESSAGE = """
            Q. 할인 금액을 입력해주세요.
            """;

    public static final String INPUT_FIXED_DISCOUNT_VALUE_MESSAGE = "Q. 할인 금액(₩)을 입력해주세요.";
    public static final String INPUT_PERCENT_DISCOUNT_VALUE_MESSAGE = "Q. 할인율(%)을 입력해주세요.";
    public static final String VOUCHER_REGISTERED = "바우처가 등록되었습니다.";
}
