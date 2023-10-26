package com.programmers.vouchermanagement.common;

public enum VoucherMessage {
    VOUCHER_COMMAND_LIST_MESSAGE("""      
            === Voucher Management ===
            0. 뒤로 가기
            1. 전체 목록
            2. ID로 검색
            3. 이름으로 검색
            4. 바우처 생성
            5. 바우처 삭제
            """),

    // Input messages
    INPUT_VOUCHER_ID_MESSAGE("Enter voucher id."),
    INPUT_VOUCHER_NAME_MESSAGE("Enter voucher name."),
    INPUT_DISCOUNT_AMOUNT_MESSAGE("Enter discount amount: "),

    // create voucher messages
    SELECT_VOUCHER_TYPE_MESSAGE("[System] Please select voucher type"),
    FIXED_AMOUNT_VOUCHER_GUIDE_MESSAGE("""
            [System] Fixed amount voucher will be created.
            [System] Enter "VOUCHER NAME" and "FIXED VALUE($)" to discount.
            """),
    PERCENT_DISCOUNT_VOUCHER_GUIDE_MESSAGE("""
            [System] Percent discount voucher will be created.
            [System] Enter "VOUCHER NAME" and "PERCENT VALUE(%)" to discount.
            """),
    VOUCHER_CREATED_MESSAGE("[System] Voucher created."),
    VOUCHER_DELETED_MESSAGE("[System] Voucher deleted.");

    private final String message;

    VoucherMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
