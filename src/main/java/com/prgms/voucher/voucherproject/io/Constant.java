package com.prgms.voucher.voucherproject.io;

public final class Constant {

    public static final String CONSOLE_MENU = """
                === Voucher Program ===
                Type **exit** to exit the program.
                Type **create** to create a new voucher.
                Type **list** to list all vouchers.""";

    public static final String CONSOLE_VOUCHER_MENU = """
                1 -- 고정 할인 Voucher 생성
                2 -- 퍼센트 할인 Voucher 생성
                입력: """;

    public static final String CREATE_FIXED_MSG = "고정 할인 금액을 입력하세요. (1이상)";

    public static final String CREATE_PERCENT_MSG = "퍼센트 할인 금액을 입력하세요. (1~99)";

}
