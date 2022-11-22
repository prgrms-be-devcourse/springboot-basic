package org.programmers.program.io;

public enum Message {
    WELCOME_MESSAGE("안녕!"),
    SELECTION_MESSAGE("\n=== Voucher Program ===" +
            "\nType **exit** to exit the program." +
            "\nType **create** to create a new voucher." +
            "\nType **list** to list all vouchers."),
    VOUCHER_TYPE("======Choose Voucher Type=====" +
            "\n 1 : Fixed Amount Voucher." +
            "\n 2 : Percent Voucher" +
            "\n press 1 or 2"),
    VOUCHER_AMOUNT("======Voucher Amount====="),
    VOUCHER_EXPIRATION_DATE("Do you want to set the expiration date? (default 1 week)");


    private final String content;
    Message(String message) {
        this.content = message;
    }

    public String getMessage(){
        return content;
    }
}
