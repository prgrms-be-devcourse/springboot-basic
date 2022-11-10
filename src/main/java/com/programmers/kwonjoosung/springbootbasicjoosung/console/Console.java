package com.programmers.kwonjoosung.springbootbasicjoosung.console;



import com.programmers.kwonjoosung.springbootbasicjoosung.controller.CommandType;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.stream.Stream;

@Component
public class Console {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = LoggerFactory.getLogger(Console.class);
    private static final String startMessage = "=== Voucher Program ===";
    private static final String inputCommand = "command >> ";
    private static final String inputVoucherType = "voucherType >> ";
    private static final String inputDiscount = "discount >> ";
    private static final String parseErrorMessage = "숫자를 입력해 주세요.";
    private static final String exitMessage = "=== Exit Program ===";

    public void startMessage() {
        System.out.println(startMessage);
    }

    public void ExitMessage() {
        System.out.println(exitMessage);
    }

    public String inputCommand() {
        System.out.print(inputCommand);
        return scanner.nextLine().trim();
    }

    public String inputVoucherType() {
        System.out.print(inputVoucherType);
        return scanner.nextLine().trim();
    }

    public long inputDiscount() {
        System.out.print(inputDiscount);
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException ne) {
            logger.error(parseErrorMessage);
            throw new RuntimeException(parseErrorMessage);
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
    public void showAllCommandSet() {
        Stream.of(CommandType.values())
                .map(CommandType::getExplanation)
                .forEach(System.out::println);
    }

    public void showError(Exception e) {
        System.out.println(e.getMessage());
    }

}
