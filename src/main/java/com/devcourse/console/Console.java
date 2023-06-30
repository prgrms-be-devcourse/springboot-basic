package com.devcourse.console;

import java.util.Scanner;

public class Console implements Reader<String>, Writer {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String GREETING = """
            === Voucher Program ===
           Type <EXIT> to exit the program.
           Type <CREATE> to create a new voucher.
           Type <LIST> to list all vouchers.
            """;
    private static final String GET_COMMAND_MESSAGE = "\nType Command : ";

    @Override
    public String read() {
        return scanner.nextLine();
    }

    @Override
    public <T> void write(T input) {
        System.out.print(input);
    }

    public void greeting() {
        write(GREETING);
    }

    public String readCommand() {
        write(GET_COMMAND_MESSAGE);
        return read();
    }
}
