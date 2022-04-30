package org.prgrms.kdt.util;

import org.prgrms.kdt.domain.command.CommandType;
import org.prgrms.kdt.domain.customer.model.CustomerType;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

public class Input {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = LoggerFactory.getLogger(Input.class);

    private Input() {

    }

    public static CommandType inputVoucherCommand() {
        while (true) {
            try {
                String commandInput = scanner.next();;
                return CommandType.findCommand(commandInput);
            } catch (IllegalArgumentException e) {
                logger.error("input voucher command error: {}", e.getMessage());
            }
        }
    }

    public static CustomerType inputCustomerType() {
        while (true) {
            try {
                String customerTypeInput = scanner.next();
                return CustomerType.findCustomerType(customerTypeInput);
            } catch (IllegalArgumentException e) {
                logger.error("input customer type error: {}", e.getMessage());
            }
        }
    }

    public static VoucherType inputVoucherType() {
        while (true) {
            try {
                String voucherTypeInput = scanner.next();
                return VoucherType.findVoucherType(voucherTypeInput);
            } catch (IllegalArgumentException e) {
                logger.error("input voucher type error: {}", e.getMessage());
            }
        }
    }

    public static long inputDiscountValue() {
        return scanner.nextLong();
    }

    public static LocalDate inputDate() {
        while (true) {
            try{
                String dateString = scanner.next();
                return LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
            } catch (DateTimeParseException e) {
                logger.error("input date error: {}", e.getMessage());
            }
        }
    }

    public static UUID inputUuid() {
        while (true) {
            try {
                String uuidString = scanner.next();
                return UUID.fromString(uuidString);
            } catch (IllegalArgumentException e) {
                logger.error("input uuid error {}:", e.getMessage());
            }
        }

    }

    public static String inputString() {
        return scanner.next();
    }
}
