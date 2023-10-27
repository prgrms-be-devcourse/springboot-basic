package org.programmers.springorder.constant;

public class Message {

    private Message() {
    }

//    public static final String MENU_SELECT_MESSAGE = """
//            === Voucher Program ===
//            1. Exit the program.
//            2. Create a new voucher.
//            3. List all vouchers.
//            4. Show all blacklist.
//            """;

    public static final String MENU_SELECT_MESSAGE = """
            === Voucher Program ===
            1. Exit
            2. Voucher
            3. Customer
            4. Wallet
            """;

    public static final String VOUCHER_MENU_SELECT_MESSAGE = """
            === Customer Menu ===
            1. Create a new voucher.
            2. Show all vouchers.
            3. Update a voucher.
            4. Delete a voucher.
            5. Back to menu.
            """;

    public static final String VOUCHER_SELECT_MESSAGE = """
            Q. 원하는 바우처 타입을 입력해주세요.
            1. Fixed Amount Voucher
            2. Percent Discount Voucher
            """;

    public static final String INPUT_FIXED_DISCOUNT_VALUE_MESSAGE = "Q. 할인 금액(₩)을 입력해주세요.";
    public static final String INPUT_PERCENT_DISCOUNT_VALUE_MESSAGE = "Q. 할인율(%)을 입력해주세요.";
    public static final String VOUCHER_REGISTERED = "바우처가 등록되었습니다.";

    public static final String INPUT_VOUCHER_ID = "Q. 바우처 아이디를 입력해주세요.";
    public static final String VOUCHER_UPDATED = "바우처가 수정되었습니다.";
    public static final String VOUCHER_DELETED = "바우처가 삭제되었습니다.";


    public static final String CUSTOMER_MENU_SELECT_MESSAGE = """
            === Customer Menu ===
            1. Create a new customer.
            2. Show all blacklist.
            3. Back to menu.
            """;

    public static final String INPUT_CUSTOMER_NAME_MESSAGE = "Q. 이름을 입력해주세요.";
    public static final String CUSTOMER_REGISTERED = "고객이 등록되었습니다.";


    public static final String BACK_TO_MENU_MESSAGE = "이전 메뉴로 돌아갑니다.";
    public static final String EXIT_PROGRAM_MESSAGE = "프로그램이 종료되었습니다.";
}
