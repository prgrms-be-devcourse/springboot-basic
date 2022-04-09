package org.programmers.kdtspringvoucherweek1.io;

import org.programmers.kdtspringvoucherweek1.error.Error;
import org.programmers.kdtspringvoucherweek1.log.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class Console implements Input, Output{
    private final Scanner scanner = new Scanner(System.in);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String END = "\u001B[0m";
    private final String YELLOW_COVER= "\u001B[33m===\u001B[0m";
    private final String EMPHASIS_EXIT = "\u001B[32mexit\u001B[0m";
    private final String BOLD = "\033[0;1m";
    private final String BOLD_CREATE = "\033[0;1mcreate\u001B[0m";
    private final String BOLD_LIST = "\033[0;1mlist\u001B[0m";

    public void mainMenu(String version) {
        System.out.println(YELLOW_COVER +  " Voucher Program " + version + YELLOW_COVER);
        System.out.println("Type " + BOLD + EMPHASIS_EXIT + " to " + EMPHASIS_EXIT + " the program.");
        System.out.println("Type " + BOLD_CREATE + " to create a new voucher.");
        System.out.println("Type " + BOLD_LIST + " to list all vouchers.");
        System.out.print("> ");
    }

    public void createMenu() {
        System.out.println("Please select the voucher you want to create by " + BOLD + "number" + END);
        System.out.println("1. FixedAmountVoucher");
        System.out.println("2. PercentDiscountVoucher");
        System.out.print("> ");
    }

    public void listMenu() {
        System.out.println("Please select the information you want to check by " + BOLD + "number" + END);
        System.out.println("1. VoucherList");
        System.out.println("2. BlackList");
        System.out.print("> ");
    }

    public String nextLine() {
        return scanner.nextLine();
    }

    public void logging(LogLevel log, String msg) {
        switch (log) {
            case TRACE -> logger.trace(msg);
            case DEBUG -> logger.debug(msg);
            case INFO -> logger.info(msg);
            case WARN -> logger.warn(msg);
            case ERROR -> logger.error(msg);
        }
    }
    public void msg(String msg) {
        System.out.println(msg);
    }

    public long discount(String kind) {
        System.out.println("Enter discount " + kind);
        System.out.print("> ");
        String inputPrice = scanner.nextLine();
        long price;
        try {
            price = Long.parseLong(inputPrice);
            if (price <= 0) {
                System.out.println(Error.INPUT_NEGATIVE_NUMBERS);
                logging(LogLevel.WARN, Error.INPUT_NEGATIVE_NUMBERS);
            }
        } catch (NumberFormatException numberFormatException) {
            System.out.println(Error.INPUT_NOT_NUMBERS);
            logging(LogLevel.WARN, Error.INPUT_NOT_NUMBERS);
            return -1;
        }
        return price;
    }
}
