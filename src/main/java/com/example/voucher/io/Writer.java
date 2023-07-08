package com.example.voucher.io;

class Writer {

    public final String MODE_TYPE_SELECTION = """
        === Voucher Program ===
        Type exit to exit the program.
        Type create to create a new voucher.
        Type list to list all vouchers.
        """;

    public final String VOUCHER_TYPE_INPUT_INFO = """
        Select VoucherType And Info
          	""";

    public final String VOUCHER_TYPE_SELECTION = """
        * Input Number for select VoucherType
        1. FixedAmount
        2. PercentDiscount
          	""";
    public final String DISCOUNT_VALUE = """
        * Input Discount Value
        """;

    public final String FIXED_AMOUNT_DISCOUNT_VOUCHER_INFO_FORMAT = "VoucherType : %s, discountAmount : %d";
    public final String PERCENT_DISCOUNT_VOUCHER_INFO_FORMAT = "VoucherType : %s, discountPercent : %d";

    public Writer() {
    }

    public void writeModeTypeSelection() {
        System.out.println(MODE_TYPE_SELECTION);
    }

    public void writeVoucherInfoRequest(){
        System.out.println(VOUCHER_TYPE_INPUT_INFO);
    }

    public void writeVoucherTypeSelection() {
        System.out.println(VOUCHER_TYPE_SELECTION);
    }

    public void writeDiscountValueRequest() {
        System.out.println(DISCOUNT_VALUE);
    }

    public void writeFixedAmountDiscountVoucherInfo(String voucherType, long value) {
        System.out.println(String.format(FIXED_AMOUNT_DISCOUNT_VOUCHER_INFO_FORMAT, voucherType, value));
    }

    public void writePercentDiscountVoucherInfo(String voucherType, long value) {
        System.out.println(String.format(PERCENT_DISCOUNT_VOUCHER_INFO_FORMAT, voucherType, value));
    }

    public void writeCustomMessage(String customMessage) {
        System.out.println(customMessage);
    }

}
