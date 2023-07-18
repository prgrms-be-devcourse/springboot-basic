package com.example.voucher.io;

class Writer {

    enum Message {
        SERVICE_TYPE_SELECTION("""
            === Voucher Management Program ===
            Type exit to exit the program.
            Type voucher to start voucher service.
            Type customer to start customer program. 
            Type wallet to start wallet program.              
            """),
        MODE_TYPE_SELECTION("""
            === Mode ===
            Type create to create a new.
            Type list to find all.
            Type delete_all to delete all.
            Type search to search by id.
            Type update to update by id.
            Type delete to delete by id.
            """),
        WALLET_MODE_TYPE_SELECTION("""
            === Mode ===
            Type create to create a new wallet.
            Type search_by_customer to list customer vouchers.
            Type search_by_voucher to list voucher customers.
            Type delete to delete voucher by id.
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
