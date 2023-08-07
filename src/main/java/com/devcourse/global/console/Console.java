package com.devcourse.global.console;

import com.devcourse.global.dto.CreateVoucherRequest;
import com.devcourse.voucher.domain.Voucher;
import com.devcourse.global.Command;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Console {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String USER_NAME_GUIDE = "\n:: Input User Name,  ::";
    private static final String GET_COMMAND_GUIDE = """
            \n:: Support Command(CREATE, LIST, BLACKLIST, EXIT) ::
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
        int discount = parser.toValidDiscountByType(type, inputDiscount);

        String inputExpiredAt = read(EXPIRATION_DATE_GUIDE);
        LocalDateTime expiredAt = parser.toValidExpiredAt(inputExpiredAt);

        return new CreateVoucherRequest(discount, expiredAt, type);
    }

    public String readUserName() {
        String name = read(USER_NAME_GUIDE);
        parser.validateName(name);
        return name;
    }
}
