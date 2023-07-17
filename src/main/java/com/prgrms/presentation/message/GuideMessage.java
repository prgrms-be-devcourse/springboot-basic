package com.prgrms.presentation.message;

public enum GuideMessage {
    START("=== Voucher Program ===" + System.lineSeparator() +
            "Type 'exit' to exit the program." + System.lineSeparator() +
            "Type 'create' to create a new voucher." + System.lineSeparator() +
            "Type 'list' to list all vouchers." + System.lineSeparator() +
            "========================" + System.lineSeparator() +
            "Type 'give voucher' to give a voucher to a customer." + System.lineSeparator() +
            "Type 'take voucher' to take a voucher from a customer." + System.lineSeparator() +
            "Type 'customer list' to see the customer list with the same voucher."
            + System.lineSeparator() +
            "Type 'voucher list' to see the voucher list of a customer."),

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
