package com.devcourse.global.console;

import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import com.devcourse.voucher.presentation.Command;
import com.devcourse.voucher.presentation.dto.ApplicationRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Console implements Reader<String>, Writer {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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
    private static final String NOT_SUPPORT_DATE_FORMAT = "[Error] Your Input is incorrect Date Format : ";
    private static final String NOT_A_NUMBER = "[Error] Your Input is not a Number : ";

    @Override
    public String read() {
        return scanner.nextLine();
    }

    @Override
    public <T> void write(T input) {
        if (isIterable(input)) {
            writeIterable(input);
            return;
        }

        System.out.print(input);
    }

    public void greeting() {
        write(GREETING);
    }

    public ApplicationRequest readRequest() {
        String readCommand = readCommand();
        Command command = Command.from(readCommand);

        if (command.isCreation()) {
            CreateVoucherRequest request = readCreationRequest();
            return ApplicationRequest.creation(request);
        }

        return ApplicationRequest.noPayload(command);
    }

    private String readCommand() {
        write(GET_COMMAND_GUIDE);
        return read();
    }

    private CreateVoucherRequest readCreationRequest() {
        StringBuilder stringBuilder = new StringBuilder();

        readInformation(stringBuilder, VOUCHER_TYPE_GUIDE);
        readInformation(stringBuilder, DISCOUNT_INT_GUIDE);
        readInformation(stringBuilder, EXPIRATION_DATE_GUIDE);

        return createVoucherRequest(stringBuilder.toString());
    }

    private void readInformation(StringBuilder stringBuilder, String message) {
        write(message);
        String input = read();
        stringBuilder.append(input).append(",");
    }

    private CreateVoucherRequest createVoucherRequest(String information) {
        StringTokenizer tokenizer = new StringTokenizer(information, ",");

        String type = tokenizer.nextToken();
        int discount = parseDiscount(tokenizer.nextToken());
        LocalDateTime expiredAt = parseExpiration(tokenizer.nextToken());

        return CreateVoucherRequest.of(type, discount, expiredAt);
    }

    private LocalDateTime parseExpiration(String expiredAt) {
        try {
            LocalDate localDate = LocalDate.parse(expiredAt, TIME_FORMAT);
            return LocalDateTime.of(localDate, LocalTime.MIDNIGHT);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(NOT_SUPPORT_DATE_FORMAT + expiredAt);
        }
    }

    private Integer parseDiscount(String discount) {
        try {
            return Integer.parseInt(discount);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(NOT_A_NUMBER + discount);
        }
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
