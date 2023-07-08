package com.example.voucher.io;

import com.example.voucher.constant.VoucherType;

class Writer {

    enum Message {
        MODE_TYPE_SELECTION("""
            === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers.
            """),
        VOUCHER_TYPE_INPUT_INFO("""
            Select VoucherType And Info
              	"""),
        VOUCHER_TYPE_SELECTION("""
            * Input Number for select VoucherType
            1. FixedAmount
            2. PercentDiscount
              	"""),
        DISCOUNT_VALUE("""
            * Input Discount Value
            """),
        INVALID_ARGUMENT_RETRY_MODE_TYPE_SELECTION("""
            유효하지 않은 값 입니다. 모드 타입을 다시 선택해주세요.
            """);

        private String text;

        Message(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public Writer() {
    }

    public static final String VOUCHER_INFO_FORMAT = "VoucherType : %s, discountValue : %d";

    public void writeMessage(VoucherType voucherType, long value) {
        System.out.println(String.format(VOUCHER_INFO_FORMAT, voucherType, value));
    }

    public void writeMessage(Message message) {
        System.out.println(message.getText());
    }

}
