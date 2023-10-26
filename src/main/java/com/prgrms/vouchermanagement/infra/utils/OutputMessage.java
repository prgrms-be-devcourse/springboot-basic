package com.prgrms.vouchermanagement.infra.utils;

public class OutputMessage {

    public static final String MENU_TYPES =
            "\n" +
            "=== Voucher Program ===\n" +
            "1. Exit the program.\n" +
            "2. Create a new voucher.\n" +
            "3. List all vouchers.\n" +
            "[exit, create, list] 중 하나를 선택해주세요.\n";

    public static final String VOUCHER_NAME =
            "Voucher 이름을 입력해주세요.\n";

    public static final String VOUCHER_TYPES =
            "원하시는 Voucher Type을 입력해주세요.\n" +
            "1. fixed\n" +
            "2. rate\n";

    public static final String FIXAMOUNT_VOUCHER_AMOUNT =
            "고정 할인 금액을 입력해주세요.\n";

    public static final String PERCENTDISCOUNT_VOUCHER_AMOUNT =
            "할인율을 입력해주세요.\n";


}
