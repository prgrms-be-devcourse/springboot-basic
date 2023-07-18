package com.example.voucher.io;

class Writer {

    enum Message {
        SERVICE_TYPE_SELECTION("""
            === Voucher Management Program ===
            Type exit to exit the program.
            Type voucher to start voucher program.
            Type customer to start voucher program.              
            """),
        MODE_TYPE_SELECTION("""
            === Mode ===
            Type create to create a new voucher.
            Type list to list all vouchers.
            Type delete_all to delete all vouchers.
            Type search to search voucher by id.
            Type update to update voucher by id.
            """),
        VOUCHER_INFO_INPUT_REQUEST("""
            Select VoucherType And Info
              	"""),
        VOUCHER_TYPE_SELECTION("""
            * Input Number for select VoucherType
            1. FixedAmount
            2. PercentDiscount
              	"""),
        CUSTOMER_TYPE_SELECTION("""
            * Input Type for select VoucherType
            NORMAL
            BLACK
              	"""),
        DISCOUNT_VALUE_INPUT_REQUEST("""
            * Input Discount Value
            """),
        ID_INPUT_REQUEST("""
            * Input Id
            """),
        NAME_INPUT_REQUEST("""
            * Input Name
            """),
        NAME_INPUT_EMAIL("""
            * Input Email
            """),
        INVALID_ARGUMENT("""
            유효하지 않은 값 입니다. 다시 입력해주세요.
            """),
        REQUEST_FAILED("""
            문제가 발생해 요청을 수행하지 못했습니다.
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

    public void writeMessage(Message message) {
        System.out.println(message.getText());
    }

    public void writeMessage(String resultInfo) {
        System.out.println(resultInfo);
    }

}
