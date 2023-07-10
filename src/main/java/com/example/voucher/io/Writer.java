package com.example.voucher.io;

import java.util.UUID;
import com.example.voucher.constant.VoucherType;

class Writer {

    enum Message {
        MODE_TYPE_SELECTION("""
            === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers.
            """),
        VOUCHER_INFO_INPUT_REQUEST("""
            Select VoucherType And Info
              	"""),
        VOUCHER_TYPE_SELECTION("""
            * Input Number for select VoucherType
            1. FixedAmount
            2. PercentDiscount
              	"""),
        DISCOUNT_VALUE_INPUT_REQUEST("""
            * Input Discount Value
            """),
        INVALID_ARGUMENT_RETRY_MODE_TYPE_SELECTION("""
            유효하지 않은 값 입니다. 모드 타입을 다시 선택해주세요.
            """),
        INVALID_ARGUMENT_CANT_CREATE_VOUCHER("""
            유효하지 않은 값입니다. 바우처를 생성할 수 없습니다.
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

    public static final String VOUCHER_INFO_TEMPLATE = "VoucherID : %s, VoucherType : %s, discountValue : %d";

    public void writeMessage(UUID voucherId, VoucherType voucherType, long discountValue) {
        System.out.println(String.format(VOUCHER_INFO_TEMPLATE, voucherId, voucherType, discountValue));
    }

    public void writeMessage(Message message) {
        System.out.println(message.getText());
    }

}
