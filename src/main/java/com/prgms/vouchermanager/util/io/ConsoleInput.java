package com.prgms.vouchermanager.util.io;


import com.prgms.vouchermanager.contorller.front.FrontController;
import com.prgms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgms.vouchermanager.domain.voucher.PercentDiscountVoucher;
import com.prgms.vouchermanager.domain.voucher.Voucher;
import com.prgms.vouchermanager.validation.InputValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.UUID;

import static com.prgms.vouchermanager.domain.voucher.VoucherType.*;
import static com.prgms.vouchermanager.exception.ExceptionType.*;
import static com.prgms.vouchermanager.exception.ExceptionType.INVALID_VOUCHER_INFO;

@Component
public class ConsoleInput {
    private final Scanner scanner;
    private final InputValidation inputValidation;
    private final ConsoleOutput consoleOutput;

    private final static Logger logger = LoggerFactory.getLogger(FrontController.class);


    public ConsoleInput() {
        this.inputValidation = new InputValidation();
        this.consoleOutput = new ConsoleOutput();
        scanner = new Scanner(System.in);
    }

    public int inputFrontMenu() {

        consoleOutput.printFrontMenu();

        int menu ;

        try {
            menu = Integer.parseInt(scanner.next());
        } catch (RuntimeException e) {
            logger.warn(e.getMessage());
            throw new RuntimeException(INVALID_FRONT_MENU.getMessage());
        }

        if (!inputValidation.validFrontMenu(menu)) {
            logger.warn(INVALID_FRONT_MENU.getMessage());
            throw new RuntimeException(INVALID_FRONT_MENU.getMessage());
        }
        return menu;
    }

    public int inputVoucherMenu() {

        consoleOutput.printVoucherMenu();

        int menu ;

        try {
            menu = Integer.parseInt(scanner.next());
        } catch (RuntimeException e) {
            logger.warn(e.getMessage());
            throw new RuntimeException(INVALID_VOUCHER_MENU.getMessage());
        }


        if (!inputValidation.validVoucherMenu(menu)) {
            logger.warn(INVALID_VOUCHER_MENU.getMessage());
            throw new RuntimeException(INVALID_VOUCHER_MENU.getMessage());
        }
        return menu;

    }

    public int inputCustomerMenu() {

        consoleOutput.printCustomerMenu();

        int menu ;

        try {
            menu = Integer.parseInt(scanner.next());
        } catch (RuntimeException e) {
            logger.warn(e.getMessage());
            throw new RuntimeException(INVALID_CUSTOMER_MENU.getMessage());
        }

        if (!inputValidation.validCustomerMenu(menu)) {
            logger.warn(INVALID_CUSTOMER_MENU.getMessage());
            throw new RuntimeException(INVALID_CUSTOMER_MENU.getMessage());
        }
        return menu;

    }

    public Voucher inputVoucher() {
        int voucherType = 0;

        Voucher voucher = null;
        try {
            voucherType = inputVoucherType();
        } catch (RuntimeException e) {
            logger.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }


        try {
            if (voucherType == FIXED_AMOUNT.getNumber()) {

                consoleOutput.printVoucherAmount();

                long amount = Integer.parseInt(scanner.next());

                voucher = new FixedAmountVoucher(UUID.randomUUID(), amount);

            } else {

                consoleOutput.printVoucherPercent();

                long percent = Integer.parseInt(scanner.next());

                if (!inputValidation.validPercent(percent)) {
                    throw new RuntimeException();
                }

                voucher = new PercentDiscountVoucher(UUID.randomUUID(), percent);
            }
        } catch (RuntimeException e) {
            logger.warn(e.getMessage());
            throw new RuntimeException(INVALID_VOUCHER_INFO.getMessage());
        }

        return voucher;
    }

    private int inputVoucherType() {

        consoleOutput.printVoucherType();

        int type ;

        try {
            type = Integer.parseInt(scanner.next());
        } catch (RuntimeException e) {
            logger.warn(e.getMessage());
            throw new RuntimeException(INVALID_VOUCHER_TYPE.getMessage());
        }


        if (inputValidation.validVoucherType(type)) {
            return type;
        } else {
            logger.warn(INVALID_VOUCHER_TYPE.getMessage());
            throw new RuntimeException(INVALID_VOUCHER_TYPE.getMessage());
        }
    }


}
