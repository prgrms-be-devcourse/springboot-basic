package org.prgrms.kdtspringdemo.view.console;

import org.prgrms.kdtspringdemo.customer.constant.CustomerCommand;
import org.prgrms.kdtspringdemo.view.constant.MainCommandType;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherCommand;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.UUID;

public class Console {
    private static final Logger logger = LoggerFactory.getLogger(Console.class);
    private static final String IS_NUMERIC_EXCEPTION_MESSAGE = "바우처의 할인양은 숫자를 입력해주세요.\n";
    private static final String UUID_FORMAT_EXCEPTION_MESSAGE = "UUID 형식이 잘못 입력되었습니다.";

    private final Scanner scanner = new Scanner(System.in);

    public void printMessage(String message) {
        System.out.print(message);
    }

    public MainCommandType inputMainCommand(Message message) {
        printMessage(message.getText());

        return MainCommandType.findCommandType(scanner.nextLine());
    }

    public VoucherCommand inputVoucherCommand(Message message) {
        printMessage(message.getText());

        return VoucherCommand.findVoucherCommand(scanner.nextLine());
    }

    public VoucherType chooseVoucherType(Message message) {
        printMessage(message.getText());

        return VoucherType.findVoucherType(scanner.nextLine());
    }

    public Long inputAmountByVoucher(Message message) {
        printMessage(message.getText());
        String inputAmount = scanner.nextLine();
        validateAmountIsNumeric(inputAmount);

        return Long.parseLong(inputAmount);
    }

    private void validateAmountIsNumeric(String amount) {
        try {
            Long.parseLong(amount);
        } catch (NumberFormatException e) {
            logger.error("원인 : {} -> 에러 메시지 : {}", amount, IS_NUMERIC_EXCEPTION_MESSAGE);
            throw new IllegalArgumentException(IS_NUMERIC_EXCEPTION_MESSAGE);
        }
    }

    public UUID inputVoucherId(Message message) {
        printMessage(message.getText());
        String userVoucherId = scanner.nextLine();
        validateVoucherId(userVoucherId);

        return UUID.fromString(userVoucherId);
    }

    private void validateVoucherId(String userId) {
        try {
            UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            logger.error("원인 : {} -> 에러 메시지 : {}", userId, IS_NUMERIC_EXCEPTION_MESSAGE);
            throw new IllegalArgumentException(UUID_FORMAT_EXCEPTION_MESSAGE);
        }
    }

    public void printVoucher(Message message, UUID userId, VoucherType userType, long amount) {
        System.out.printf(message.getText(), userId.toString(), userType, amount);
    }

    public CustomerCommand inputCustomerCommand(Message message) {
        printMessage(message.getText());

        return CustomerCommand.findCustomerCommand(scanner.nextLine());
    }

    public String inputCustomerNickname(Message message) {
        printMessage(message.getText());

        return scanner.nextLine();
    }

    public void printCustomer(Message message, UUID id, String nickname) {
        System.out.printf(message.getText(), id.toString(), nickname);
    }

    public UUID inputCustomerId(Message message) {
        printMessage(message.getText());
        String userCustomerId = scanner.nextLine();
        validateCustomerId(userCustomerId);

        return UUID.fromString(userCustomerId);
    }

    private void validateCustomerId(String userId) {
        try {
            UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            logger.error("원인 : {} -> 에러 메시지 : {}", userId, IS_NUMERIC_EXCEPTION_MESSAGE);
            throw new IllegalArgumentException(UUID_FORMAT_EXCEPTION_MESSAGE);
        }
    }
}
