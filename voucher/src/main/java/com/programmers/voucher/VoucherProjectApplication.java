package com.programmers.voucher;

import com.programmers.voucher.config.ApplicationMessages;
import com.programmers.voucher.entity.voucher.DiscountType;
import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.service.customer.CustomerService;
import com.programmers.voucher.service.customer.CustomerVoucherService;
import com.programmers.voucher.service.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;

import java.io.*;
import java.util.List;
import java.util.function.Function;

@SpringBootApplication
public class VoucherProjectApplication {

    private static ApplicationMessages applicationMessages;
    private static CustomerVoucherService customerVoucherService;
    private static VoucherService voucherService;
    private static CustomerService blacklistCustomerService;
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final Logger log = LoggerFactory.getLogger(VoucherProjectApplication.class);

    private static final String INVALID_COMMAND = "Unsupported command. Please try again.";

    public static String acquireInput(String msg) {
        try {
            bw.write(msg);
            bw.flush();
            return br.readLine().strip();
        } catch (IOException ex) {
            log.warn("IOException occur when reading user input.");
            return "";
        }
    }

    public static <T> T acquireInputUntil(String msg, Function<String, T> convert) {
        T result = null;
        boolean allowed = false;
        while (!allowed) {
            String input = "";
            while (input.isBlank()) input = acquireInput(msg);

            try {
                result = convert.apply(input);
                allowed = true;
            } catch (RuntimeException ex) {
                log.warn("{} - {}", ex.getClass().getName(), ex.getLocalizedMessage());
            }
        }
        return result;
    }

    public static void printOutput(String delim, Object... args) {
        try {
            for (Object arg : args) {
                bw.write(arg.toString());
                if ("\n".equals(delim) || "\r\n".equals(delim)) {
                    bw.newLine();
                } else {
                    bw.write(delim);
                }
            }

            bw.flush();
        } catch (IOException ex) {
            log.warn("IOException occur when printing output.");
        }
    }

    public enum Command {
        CREATE_VOUCHER("create_voucher", () -> {
            String voucherName = acquireInputUntil(applicationMessages.getRequireName(), string -> string);
            DiscountType voucherType = acquireInputUntil(applicationMessages.getRequireType(), DiscountType::of);
            int voucherAmount = acquireInputUntil(applicationMessages.getRequireAmount(), Integer::parseUnsignedInt);
            long customerId = acquireInputUntil(applicationMessages.getRequireCustomerId(), Long::parseUnsignedLong);
            printOutput("", voucherService.create(voucherName, voucherType, voucherAmount, customerId));
        }),
        LIST_VOUCHER("list_voucher", () -> {
            printOutput("\n", "======= [ VOUCHERS ] =======");
            voucherService.listAll().forEach(voucher -> printOutput("\n", voucher));
            printOutput("\n", "============================");
        }),
        READ_VOUCHER("read_voucher", () -> {
            long voucherId = acquireInputUntil(applicationMessages.getRequireVoucherId(), Long::parseUnsignedLong);
            voucherService.findById(voucherId).ifPresentOrElse(
                    voucher -> printOutput("", voucher),
                    () -> printOutput("", "NO VOUCHER FOUND."));
        }),
        UPDATE_VOUCHER("update_voucher", () -> {
            long voucherId = acquireInputUntil(applicationMessages.getRequireVoucherId(), Long::parseUnsignedLong);
            voucherService.findById(voucherId).ifPresentOrElse(
                    voucher -> {
                        Voucher.UpdatableField updateType = acquireInputUntil(applicationMessages.getRequireUpdateField(), Voucher.UpdatableField::of);
                        String updateValue = acquireInputUntil(applicationMessages.getRequireUpdateValue(), string -> string);
                        voucher.update(updateType, updateValue);
                        voucherService.update(voucher);
                    },
                    () -> printOutput("", "NO VOUCHER FOUND.")
            );
        }),
        DELETE_VOUCHER("delete_voucher", () -> {
            long voucherId = acquireInputUntil(applicationMessages.getRequireVoucherId(), Long::parseUnsignedLong);
            voucherService.delete(voucherId);
        }),
        BLACKLIST("blacklist", () -> {
            printOutput("\n", "====== [ BLACKLIST ] ======");
            blacklistCustomerService.listAll().forEach(customer -> printOutput("\n", customer));
            printOutput("\n", "===========================");
        }),
        INTRO("intro", () -> printOutput("\n", applicationMessages.getIntroMessage())),
        UNKNOWN("unknown", () -> log.warn(INVALID_COMMAND)),
        CREATE_USER("create_user", () -> {
            boolean allowed = false;
            while (!allowed) {
                String username = acquireInputUntil("Type username: ", string -> string);
                String alias = acquireInputUntil("Type alias: ", string -> string);
                try {
                    printOutput("", customerVoucherService.create(username, alias));
                    allowed = true;
                } catch (DuplicateKeyException ex) {
                    log.warn("Duplicated username. Please try another username.");
                } catch (DataAccessException ex) {
                    log.warn("DataAccessException occur. Probably customer id is not valid.");
                }
            }
        }),
        LIST_USER("list_user", () -> {
            printOutput("\n", "======== [ USERS ] ========");
            customerVoucherService.listAll().forEach(customer -> printOutput("\n", customer));
            printOutput("\n", "===========================");
        }),
        LIST_USER_VOUCHER("list_user_voucher", () -> {
            long customerId = acquireInputUntil(applicationMessages.getRequireCustomerId(), Long::parseUnsignedLong);
            final List<Voucher> allByCustomer = customerVoucherService.findAllVoucherByCustomer(customerId);
            printOutput("\n", "==== [ VOUCHERS ] ====");
            allByCustomer.forEach(voucher -> printOutput("\n", voucher));
            printOutput("\n", "======================");
        }),
        READ_USER_BY_VOUCHER("read_user_by_voucher", () -> {
            long voucherId = acquireInputUntil(applicationMessages.getRequireVoucherId(), Long::parseUnsignedLong);
            customerVoucherService.findCustomerByVoucher(voucherId).ifPresentOrElse(
                    customer -> printOutput("\n", customer),
                    () -> printOutput("\n", "NO CUSTOMER FOUND."));
        });

        String name;
        Runnable behavior;

        Command(String name, Runnable behavior) {
            this.name = name;
            this.behavior = behavior;
        }

        public static Command of(String input) {
            try {
                return Command.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException ex) {
                return Command.UNKNOWN;
            }
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = SpringApplication.run(VoucherProjectApplication.class);

        voucherService = applicationContext.getBean(VoucherService.class);
        voucherService.openStorage();

        customerVoucherService = applicationContext.getBean("basicCustomerService", CustomerVoucherService.class);
        customerVoucherService.openStorage();

        blacklistCustomerService = applicationContext.getBean("blacklistCustomerService", CustomerService.class);
        blacklistCustomerService.openStorage();
        Command.BLACKLIST.behavior.run();

        applicationMessages = applicationContext.getBean(ApplicationMessages.class);

        for (String input = ""; !"exit".equals(input); input = br.readLine()) {
            Command.of(input).behavior.run();
            Command.INTRO.behavior.run();
        }

        voucherService.closeStorage();
    }
}
