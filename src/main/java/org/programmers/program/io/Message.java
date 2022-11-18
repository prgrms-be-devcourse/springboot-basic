package org.programmers.program.io;

public enum Message {
    WELCOME_MESSAGE("안녕!"),
    SELECTION_MESSAGE("\n=== Voucher Program ===" +
            "\nType **exit** to exit the program." +
            "\nType **create** to create a new voucher." +
            "\nType **list** to list all vouchers.");


    private final String content;
    Message(String message) {
        this.content = message;
    }

    public static String getMessage(Message request){
        return request.content;
    }
}
