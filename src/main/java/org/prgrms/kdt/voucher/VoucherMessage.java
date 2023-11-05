package org.prgrms.kdt.voucher;

public enum VoucherMessage {
    SELECT_VOUCHER_MENU("=== Voucher Program ===" + System.lineSeparator() +
            "[1] Type 'create' to create a new voucher." + System.lineSeparator() +
            "[2] Type 'owner' to view customer with a specific voucher." + System.lineSeparator() +
            "[3] Type 'remove' to remove a specific voucher." + System.lineSeparator() +
            "[4] Type 'find' to find all vouchers." + System.lineSeparator() +
            "[5] Type 'details' to view details of a specific voucher." + System.lineSeparator()),
    CREATE_VOUCHER_TYPE("fixed, percent 중에서 만들 바우처의 타입을 입력해주세요 : "),
    CREATE_FIXED_VOUCHER("고정으로 할인되는 비용을 입력해주세요 : "),
    CREATE_PERCENT_VOUCHER("할인되는 퍼센트를 정수로 입력해주세요 : "),
    INPUT_VOUCHER_ID("바우처의 아이디를 입력해주세요 : "),
    VOUCHER_IS_EMPTY("조회한 바우처가 하나도 존재하지 않습니다."),

    EXCEPTION_VOUCHER_TYPE("올바른 바우처 타입을 입력하세요."),
    EXCEPTION_VOUCHER_ID("올바른 바우처 아이디를 입력하세요."),
    EXCEPTION_FIXED_AMOUNT_MINUS("할인되는 비용이 0보다 커야합니다."),
    EXCEPTION_FIXED_AMOUNT_OVER("할인되는 비용이 너무 큽니다. 10만원 밑으로 설정해주세요."),
    EXCEPTION_PERCENT_MINUS("할인되는 퍼센트가 0보다 커야합니다."),
    EXCEPTION_PERCENT_OVER("할인되는 퍼센트가 100을 넘을 수 없습니다."),
    EXCEPTION_FIND_VOUCHER(" -> 해당 id의 바우처를 찾을 수 없습니다."),
    EXCEPTION_VOUCHER_ROW_MAPPER("바우처의 ROW MAPPER 가 실패했습니다."),
    EXCEPTION_NOT_EXIST_VOUCHER("존재하지 않는 바우처입니다.");

    private final String message;
    private static final String lineSeparator = System.lineSeparator();

    public String getMessage() {
        return message;
    }

    VoucherMessage(String message) {
        this.message = message;
    }
}
