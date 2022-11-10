package org.programmers.weekly.mission.util.type;

public enum Message {
    SELECT_OPTION_MESSAGE("""
            
            === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers.
            Type blacklist to list black list.
            =>\s"""),
    SELECT_VOUCHER_MESSAGE("""

            === Voucher Select ===
            Fixed Amount Voucher 을 생성하려면 띄어쓰기 없이 입력하세요. -> FixedAmountVoucher
            Percent Discount Voucher 을 생성하려면 띄어쓰기 없이 입력하세요. -> PercentDiscountVoucher
            =>\s"""),
    SELECT_VOUCHER_INFO("""

            === Voucher Info ===
            바우처의 할인 정보(할인액 또는 할인율)를 입력하세요.
            단위 없이 숫자만 입력하세요.
            =>\s"""),
    VOUCHER_LIST("""
            
            === Voucher List ===
            """),
    BLACK_LIST("""
            
            === BLACK List ===
            """),
    INPUT_ERROR_MESSAGE("잘못된 입력값 입니다.");


    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
