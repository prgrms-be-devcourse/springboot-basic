package com.prgrms.voucher_manager.io;

public enum Message {
    MAIN_MENU("=== VoucherService Program === \n" +
            "Type exit to exit the program.\n" +
            "Type voucher to use voucher service\n" +
            "Type customer to use customer service"),
    VOUCHER_MENU("=== Voucher Menu === \n" +
            "Type return to return to the previous menu.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all vouchers.\n" +
            "Type customer to search for customers with specific type voucher."),
    CREATE_VOUCHER("\n=== Create Voucher ===\n" +
            "Type fix to create FixedAmountVoucher \n" +
            "Type percent to create PercentAmountVoucher"),
    SELECT_VOUCHER("\n=== Select Voucher ===\n" +
            "Type fix to list with FixedAmountVoucher \n" +
            "Type percent to list with PercentAmountVoucher"),
    CUSTOMER_MENU("\n=== Customer Menu ===\n" +
            "Type return to return to the previous menu.\n" +
            "Type create to create a new customer. \n" +
            "Type voucher to create a new voucherWallet. \n" +
            "Type blacklist to list all blacklist customers. \n"+
            "Type list to list all customers."),
    WALLET_MENU("\n=== Wallet Menu ===\n" +
            "Type return to return to the previous menu.\n" +
            "Type insert to insert new voucher. \n" +
            "Type delete to delete a voucher. \n" +
            "Type list to list voucher wallet."),
    WRONG_INPUT("잘못된 입력 값입니다. 다시 입력해 주세요."),
    EMPTY_REPOSITORY("해당 리스트에 정보가 없습니다."),
    EMPTY_BLACKLIST("블랙리스트가 비어있습니다."),
    EXIT_PROGRAM("프로그램을 종료합니다.");


    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
