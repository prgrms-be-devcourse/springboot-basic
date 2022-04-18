package com.mountain.voucherApp.constants;

import com.mountain.voucherApp.voucher.Voucher;

public class Message {

    // discount
    public static final String FIXED_DISCOUNT = "고정 할인";
    public static final String PERCENT_DISCOUNT = "비율 할인";

    public static final String FILE_INSERT_ERROR = "file insert error";
    public static final String FILE_READ_ERROR = "file read error";
    public static final String NEGATIVE_AMOUNT_ERROR = "Amount should be positive";
    public static final String MAX_MORE_ERROR = "Amount should be less than ";
    public static final String EMPTY_RESULT_ERROR = "Got empty result";
    public static final String CREATE_NEW_FILE = "create new file";
    public static final String CREATE_NEW_VOUCHER = "create new voucher";
    public static final String PROGRAM_EXIT = "program exit";
    public static final String SHOW_VOUCHER_LIST = "show voucher list";

    // menu
    public static final String EXIT = "exit";
    public static final String CREATE = "create";
    public static final String LIST = "list";
    public static final String ADD_VOUCHER = "add_voucher";
    public static final String CUSTOMER_LIST = "customer_list";
    public static final String REMOVE_VOUCHER = "remove_voucher";
    public static final String LIST_BY_VOUCHER = "list_by_voucher";
    public static final String EXIT_PROGRAM_DESC = "to exit the program.";
    public static final String CREATE_VOUCHER_DESC = "to create a new voucher.";
    public static final String LIST_VOUCHERS_DESC = "to list all vouchers.";
    public static final String ADD_VOUCHER_DESC = "특정 고객에게 바우처를 할당할 수 있다.";
    public static final String CUSTOMER_LIST_DESC = "고객이 어떤 바우처를 보유하고 있는지 조회할 수 있어야 한다.";
    public static final String REMOVE_VOUCHER_DESC = "고객이 보유한 바우처를 제거할 수 있어야 한다.";
    public static final String LIST_BY_VOUCHER_DESC = "특정 바우처를 보유한 고객을 조회할 수 있어야 한다.";
    // console
    public static final String MANUAL_TITLE = "=== Voucher Program ===";
    public static final String WRONG_INPUT = "올바르지 않은 입력입니다.";
    public static final String PLEASE_AMOUNT = "Please enter discount amount";
    public static final String TYPE = "Type";
    public static final String PLEASE_INPUT_NUMBER = "[ 번호를 입력해 주세요. ]";
    public static final String EMPTY_DATA = "등록 된 데이터가 없습니다.";
    // jdbc
    public static final String NOT_INSERTED = "Insert가 수행되지 않았습니다.";
    public static final String NOT_UPDATED = "Update가 수행되지 않았습니다.";
    public static final String EMPTY_RESULT = "조회 결과가 존재하지 않습니다.";
    public static final String EXIST_VOUCHER = "존재하는 바우처 입니다.";
    // format
    public static final String CUSTOMER_BY_VOUCHER_FORMAT = "===== [{0}] Voucher 가 보유한 고객 리스트 =====";

}
