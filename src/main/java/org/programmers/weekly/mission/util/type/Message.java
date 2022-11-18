package org.programmers.weekly.mission.util.type;

public enum Message {
    SELECT_OPTION_MESSAGE("""
            
            === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers.
            Type wallet to use functions about wallet.
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
    CUSTOMER_LIST("""
            
            === Customer List ===
            """),
    INPUT_ERROR_MESSAGE("잘못된 입력값 입니다."),
    SELECT_WALLET_MESSAGE("""
            
            === Wallet ===
            [create] 특정 고객에게 바우처를 할당하려면 create 를 입력하세요.
            [voucher] 고객이 어떤 바우처를 보유하고 있는 지 조회하려면 voucher 을 입력하세요.
            [delete] 고객이 보유한 바우처를 제거하려면 delete 를 입력하세요.
            [customer] 특정 바우처를 보유한 고객을 조회하려면 customer 을 입력하세요.
            =>\s"""),
    INPUT_CUSTOMER_VOUCHER_MESSAGE("""
            
            customerId와 voucherId를 입력하세요. (ex. customerId voucherId)
            =>\s"""),
    INPUT_CUSTOMER_ID("고객 id를 입력해주세요 : "),
    INPUT_VOUCHER_ID("바우처 id를 입력해주세요 : ");


    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
