package com.devcourse.global.console;

import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import com.devcourse.voucher.domain.Voucher;
import com.devcourse.voucher.presentation.Command;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Console {
    private static final Scanner scanner = new Scanner(System.in);
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

    private final IoParser parser = new IoParser();

    public void print(String input) {
        System.out.println(input);
    }

    public void print(List<String> requests) {
        for (String request : requests) {
            System.out.println(request);
        }
    }

    public String read(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public Command readCommand() {
        String input = read(GET_COMMAND_GUIDE);
        return parser.parseCommand(input);
    }

    public CreateVoucherRequest readCreationRequest() {
        String inputType = read(VOUCHER_TYPE_GUIDE);
        Voucher.Type type = parser.parseVoucherType(inputType);

        String inputDiscount = read(DISCOUNT_INT_GUIDE);
        int discount = parser.parseDiscount(inputDiscount);

        String inputExpiredAt = read(EXPIRATION_DATE_GUIDE);
        LocalDateTime expiredAt = parser.parseExpiration(inputExpiredAt);

        return new CreateVoucherRequest(discount, expiredAt, type);
    }
}
