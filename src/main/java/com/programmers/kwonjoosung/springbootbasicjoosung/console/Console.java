package com.programmers.kwonjoosung.springbootbasicjoosung.console;

import com.programmers.kwonjoosung.springbootbasicjoosung.console.message.ResponseMessage;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.UUID;

@Component
public class Console {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = LoggerFactory.getLogger(Console.class);
    private static final String DELIMITER = " ";
    private static final String START_MESSAGE = "=== Voucher Program ===";
    private static final String EXIT_MESSAGE = "=== Exit Program ===";
    private static final String INPUT_COMMAND = "command >> ";
    private static final String INPUT_VOUCHER_TYPE = "voucherType >> ";
    private static final String INPUT_DISCOUNT = "discount >> ";
    private static final String INPUT_VOUCHER_ID = "voucherId >> ";
    private static final String INPUT_CUSTOMER_ID = "customerId >> ";
    private static final String INPUT_CUSTOMER_NAME = "name >>";
    private static final String PARSE_ERROR_MESSAGE = "숫자를 입력해 주세요";
    private static final String ERROR = "오류입니다!";


    public void startProgramMessage() {
        System.out.println(START_MESSAGE);
    }

    public void exitMessage() {
        System.out.println(EXIT_MESSAGE);
    }

    public Request getRequest() {
        System.out.print(INPUT_COMMAND);
        return RequestMapper.map(scanner.nextLine().trim(), DELIMITER);
    }

    public void printMessage(ResponseMessage message) {
        System.out.println(message.getMessage());
    }

    public void printError(ResponseMessage message) {
        System.out.println(ERROR);
        System.out.println(message.getMessage());
    }

    public UUID getVoucherId() {
        System.out.print(INPUT_VOUCHER_ID);
        return UUID.fromString(scanner.nextLine().trim());
    }

    public String getVoucherType() {
        System.out.print(INPUT_VOUCHER_TYPE);
        return scanner.nextLine().trim();
    }

    public long getDiscount() {
        System.out.print(INPUT_DISCOUNT);
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException ne) {
            logger.error(PARSE_ERROR_MESSAGE);
            throw new RuntimeException(PARSE_ERROR_MESSAGE);
        }
    }

    public void printVoucher(Voucher voucher) {
        System.out.println(voucher);
    }

    public void printCustomer(Customer customer) {
        System.out.println(customer);
    }

    public UUID getCustomerId() {
        System.out.print(INPUT_CUSTOMER_ID);
        return UUID.fromString(scanner.nextLine().trim());
    }

    public String getCustomerName() {
        System.out.print(INPUT_CUSTOMER_NAME);
        return scanner.nextLine().trim();
    }

    public void printHelpMessage() {
    }

    public void print(String message) {
        System.out.println(message);
    }
}
