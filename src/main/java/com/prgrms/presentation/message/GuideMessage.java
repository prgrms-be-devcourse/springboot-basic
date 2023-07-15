package com.prgrms.presentation.message;

public enum GuideMessage {
    START("=== Voucher Program === \n" +
            "Type exit to exit the program.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all vouchers.\n" +
            "========================\n" +
            "Type give voucher to give voucher to custmer.\n" +
            "Type take voucher to take a voucher from customer.\n" +
            "Type customer list to see customer list with same voucher.\n" +
            "Type voucher list to see voucher list of customer."),

    GIVE_VOUCHER("Type customer id you want to give voucher to."),
    WANT_TO_VOUCHER_ID("Type voucher id you want to give to customer."),

    TAKE_VOUCHER("Type customer id you want to take voucher from."),
    TAKE_FROM_VOUCHER_ID("Type voucher id you want to take from customer."),

    CUSTOMER_LIST("Type voucher id you want see customer list."),

    WALLET_LIST("Type customer id you want see voucher list."),

    CLOSE("바우처 어플리케이션을 종료합니다."),
    COMPLETE_CREATE("바우처가 등록되었습니다.");

    private final String message;

    GuideMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
