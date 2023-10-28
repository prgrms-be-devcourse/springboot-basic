package com.prgms.vouchermanager.util.io;

import com.prgms.vouchermanager.exception.ExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.UUID;

import static com.prgms.vouchermanager.exception.ExceptionType.*;
import static com.prgms.vouchermanager.exception.ExceptionType.INVALID_VOUCHER_INFO;

@Slf4j
@Component
public class ConsoleInput {
    private final Scanner scanner = new Scanner(System.in);

    private int inputMenu(ExceptionType type) {
        try {
            return scanner.nextInt();
        } catch (RuntimeException e) {
            scanner.next();
            throw new RuntimeException(type.getMessage());
        }
    }

    public int inputFrontMenu() {
        return inputMenu(INVALID_FRONT_MENU);
    }

    public int inputVoucherMenu() {
        return inputMenu(INVALID_VOUCHER_MENU);
    }

    public int inputCustomerMenu() {

        return inputMenu(INVALID_CUSTOMER_MENU);
    }

    public int inputVoucherType() {

        return inputMenu(INVALID_VOUCHER_TYPE);
    }

    public int inputBlackList() {

        return inputMenu(INVALID_CUSTOMER_INFO);
    }

    public long inputVoucherValue() {
        try {
            return scanner.nextLong();
        } catch (RuntimeException e) {
            throw new RuntimeException(INVALID_VOUCHER_INFO.getMessage());
        }
    }

    public UUID inputVoucherId() {
        try {
            return UUID.fromString(scanner.next());
        } catch (RuntimeException e) {
            throw new RuntimeException(INVALID_VOUCHER_INFO.getMessage());
        }
    }

    public String inputCustomerName() {
        return scanner.next();
    }

    public String inputCustomerEmail() {
        return scanner.next();
    }

    public Long inputCustomerId() {
        try {
            return scanner.nextLong();
        } catch (RuntimeException e) {
            throw new RuntimeException(INVALID_CUSTOMER_INFO.getMessage());
        }
    }
}
