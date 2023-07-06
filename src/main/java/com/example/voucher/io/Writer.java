package com.example.voucher.io;

public class Writer {

    public final String MODE_TYPE_SELECTION = """
        === Voucher Program ===
        Type exit to exit the program.
        Type create to create a new voucher.
        Type list to list all vouchers.
        """;
    public final String VOUCHER_TYPE_SELECTION = """
        Input Number for select VoucherType
        1. FixedAmount
        2. PercentDiscount
          	""";
    public final String DISCOUNT_PRICE_REQUEST = """
        input price for discount
        """;
    public final String DISCOUNT_PERCENT_REQUEST = """
        input percent for discount
        """;
    public final String RETRY_MODE_TYPE_SELECTION = """
        MODE를 다시 선택해주세요
        """;
    public final String RETRY_VOUCHER_TYPE_SELECTION = """
        Voucher Type을 다시 선택해주세요
        """;
    public final String FIXED_AMOUNT_DISCOUNT_VOUCHER_INFO_FORMAT = "VoucherType : %s, discountAmount : %d";
    public final String PERCENT_DISCOUNT_VOUCHER_INFO_FORMAT = "VoucherType : %s, discountPercent : %d";

    public Writer() {

    }

    public void writeModeTypeSelection() {
        System.out.println(MODE_TYPE_SELECTION);
    }

    public void writeVoucherTypeSelection() {
        System.out.println(VOUCHER_TYPE_SELECTION);
    }

    public void writeDiscountAmountRequest() {
        System.out.println(DISCOUNT_PRICE_REQUEST);
    }

    public void writeDiscountPercentRequest() {
        System.out.println(DISCOUNT_PERCENT_REQUEST);
    }

    public void writeRetryModeTypeSelection() {
        System.out.println(RETRY_MODE_TYPE_SELECTION);
    }

    public void writeRetryVoucherTypeSelection() {
        System.out.println(RETRY_VOUCHER_TYPE_SELECTION);
    }

    public void writeFixedAmountDiscountVoucherInfo(String voucherType, long value) {
        System.out.println(String.format(FIXED_AMOUNT_DISCOUNT_VOUCHER_INFO_FORMAT, voucherType, value));
    }

    public void writePercentDiscountVoucherInfo(String voucherType, long value) {
        System.out.println(String.format(PERCENT_DISCOUNT_VOUCHER_INFO_FORMAT, voucherType, value));
    }

}
