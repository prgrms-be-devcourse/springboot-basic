package com.prgms.vouchermanager.util.io;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.UUID;

import static com.prgms.vouchermanager.exception.ExceptionType.*;
import static com.prgms.vouchermanager.exception.ExceptionType.INVALID_VOUCHER_INFO;

@Slf4j
@Component
public class ConsoleInput {
    private final Scanner scanner;


    public ConsoleInput() {
        scanner = new Scanner(System.in);
    }

    public int inputFrontMenu() {
        try {
            return scanner.nextInt();
        } catch (RuntimeException e) {
            log.warn(e.getMessage());
            scanner.next();
            throw new RuntimeException(INVALID_FRONT_MENU.getMessage());
        }
    }

    public int inputVoucherMenu() {

        int menu ;

        try {
            menu = scanner.nextInt();
        } catch (RuntimeException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(INVALID_VOUCHER_MENU.getMessage());
        }
        return menu;

    }

    public int inputCustomerMenu() {

        int menu;

        try {
            menu = scanner.nextInt();
        } catch (RuntimeException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(INVALID_CUSTOMER_MENU.getMessage());
        }

        return menu;
    }

    public int inputVoucherType() {
        try {
            return scanner.nextInt();
        } catch (RuntimeException e) {
            throw new RuntimeException(INVALID_VOUCHER_TYPE.getMessage());
        }
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
        }catch (RuntimeException e) {
            throw new RuntimeException(INVALID_VOUCHER_INFO.getMessage());
        }
    }

    public String inputCustomerName() {
        return scanner.next();
    }

    public String inputCustomerEmail() {
        return scanner.next();
    }

    public int inputBlackList() {
        try {
            return  scanner.nextInt();
        } catch (RuntimeException e) {
            throw new RuntimeException(INVALID_CUSTOMER_INFO.getMessage());
        }
    }

    public Long inputCustomerId() {
        try {
            return scanner.nextLong();
        } catch (RuntimeException e) {
            throw new RuntimeException(INVALID_CUSTOMER_INFO.getMessage());
        }
    }
}
