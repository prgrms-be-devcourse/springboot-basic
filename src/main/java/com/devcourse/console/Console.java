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
    private static final String GET_COMMAND_GUIDE = """
            \n:: Support Command(CREATE, LIST, EXIT) ::
            Type Command :\s""";
    private static final String VOUCHER_TYPE_GUIDE = """
            \n:: Support Type(fixed, percent) ::
            Type type of Voucher :\s""";
    private static final String DISCOUNT_INT_GUIDE = """
            \n:: If typed Fixed type Amount, If not type Rate (0~100%) ::
            Type discount :\s""";
    private static final String EXPIRATION_DATE_GUIDE = """
            \n:: Support Format(yyyy-MM-dd) ::
            Type type of Voucher :\s""";

    @Override
    public String read() {
        return scanner.nextLine();
    }

    @Override
    public <T> void write(T input) {
        if (isIterable(input)) {
            writeIterable(input);
        } else {
            System.out.print(input);
        }
    }

    public void greeting() {
        write(GREETING);
    }

    public String readCommand() {
        write(GET_COMMAND_GUIDE);
        return read();
    }

    public String readVoucherInformation() {
        StringBuilder stringBuilder = new StringBuilder();

        readInformation(stringBuilder, VOUCHER_TYPE_GUIDE);
        readInformation(stringBuilder, DISCOUNT_INT_GUIDE);
        readInformation(stringBuilder, EXPIRATION_DATE_GUIDE);

        return stringBuilder.toString();
    }

    private void readInformation(StringBuilder stringBuilder, String message) {
        write(message);
        stringBuilder.append(read()).append(",");
    }

    private <T> void writeIterable(T iterable) {
        for (Object content : (Iterable) iterable) {
            System.out.println(content.toString());
        }
    }
    
    private <T> boolean isIterable(T input) {
        return input instanceof Iterable<?>;
    }
}
