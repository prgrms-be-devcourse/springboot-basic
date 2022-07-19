package com.programmers.part1.io.message;

public class Message {

    private Message() {}

    public static final String START_PROMPT = """
            === Voucher Program===
            Type "1" Create new Customer
            Type "2. Find customers"
            Type "3. Create new Voucher"
            Type "4. Find Vouchers"
            Type "5. Add Voucher to Customer"
            Type "6. Find customers by voucherId"
            Type "7. Delete Voucher from Customer"
            Type "8. Find vouchers by customerId"
            Type "exit" to exit the program..
            """ ;

    public static final String CUSTOMER_ID_PROMPT = "고객의 아이디를 입력하시오.\n";
    public static final String CUSTOMER_NAME_PROMPT = "이름을 입력하시오.\n";
    public static final String CUSTOMER_EMAIL_PROMPT = "이메일을 입력하시오.\n";
    
    public static final String VOUCHER_PROMPT = """
                ===바우처의 타입 선택하시오===
                1. 고정 금액 할인
                2. 비율 금액 할인
                """;
    public static final String VOUCHER_ID_PROMPT = "바우처의 아이디를 입력하시오.\n";
    public static final String AMOUNT_PROMPT = "할인 금액이나 비율 값을 입력하시오.\n";
    public static final String CREATE_SUCCESS = "저장에 성공하였습니다.\n";
    public static final String DELETE_SUCCESS = "삭제에 성공하였습니다.\n";
    public static final String VIEW_SUCCESS = "모든 리스트를 조회하였습니다.\n";
    public static final String EXIT = "프로그램을 종료합니다.\n";


}
