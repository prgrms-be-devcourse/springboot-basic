package com.programmers.kwonjoosung.springbootbasicjoosung.console;

import com.programmers.kwonjoosung.springbootbasicjoosung.controller.CommandType;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

@Component
public class Console {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = LoggerFactory.getLogger(Console.class);

    private static final String START_MESSAGE = "=== Voucher Program ===";
    private static final String INPUT_COMMAND = "command >> ";
    private static final String INPUT_VOUCHER_TYPE = "voucherType >> ";
    private static final String INPUT_DISCOUNT = "discount >> ";
    private static final String PARSE_ERROR_MESSAGE = "숫자를 입력해 주세요.";
    private static final String EXIT_MESSAGE = "=== Exit Program ===";

    public void startMessage() {
        System.out.println(START_MESSAGE);
    }

    public void exitMessage() {
        System.out.println(EXIT_MESSAGE);
    }

    public String inputCommand() {
        System.out.print(INPUT_COMMAND);
        return scanner.nextLine().trim();
    }

    public String inputVoucherType() {
        System.out.print(INPUT_VOUCHER_TYPE);
        return scanner.nextLine().trim();
    }

    public long inputDiscount() {
        System.out.print(INPUT_DISCOUNT);
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException ne) {
            logger.error(PARSE_ERROR_MESSAGE);
            throw new RuntimeException(PARSE_ERROR_MESSAGE);
        }
    }

    public void showVoucher(Voucher voucher) {
        System.out.println(voucher);
    }

//    제네릭을 사용하는게 더 나은 방법인가요?
//    public <T> void show(T data) { System.out.println(data); }

    public void showCustomer(Customer customer) {
        System.out.println(customer);
    }

    public void showError(Exception e) {
        System.out.println(e.getMessage());
    }

    public void showAllCommandSet() {
        CommandType.getAllExplanation().forEach(System.out::println);
    }

}
