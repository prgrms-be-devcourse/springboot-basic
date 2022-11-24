package org.prgrms.java.common;

import org.prgrms.java.controller.CustomerController;
import org.prgrms.java.controller.VoucherController;
import org.prgrms.java.domain.customer.Customer;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.exception.CommandException;
import org.prgrms.java.exception.CustomerException;
import org.prgrms.java.exception.VoucherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class VoucherProcessor implements ApplicationRunner {
    private final CustomerController customerController;
    private final VoucherController voucherController;
    private final View view;
    private static final Logger logger = LoggerFactory.getLogger(VoucherProcessor.class);

    public VoucherProcessor(CustomerController customerController, VoucherController voucherController, View view) {
        this.customerController = customerController;
        this.voucherController = voucherController;
        this.view = view;
    }

    @Override
    public void run(ApplicationArguments args) {
        boolean isRunning = true;
        while (isRunning) {
            try {
                isRunning = processCommand();
            } catch (Exception e) {
                view.print(e.getMessage());
                logger.error(e.getMessage());
                isRunning = false;
            }
        }
    }

    private boolean processCommand() {
        boolean isRunning = true;
        view.print(MessageGuide.COMMAND_OPTION);
        try {
            switch (Command.get(view.read())) {
                case EXIT:
                    view.print("Terminates the program...");
                    return isRunning = false;
                case VOUCHER:
                    processVoucherCommand();
                    break;
                case CUSTOMER:
                    processCustomerCommand();
                    break;
                case WALLET:
                    processWalletCommand();
                    break;
            }
            return true;
        } catch (CommandException e) {
            view.print(e.getMessage());
            logger.warn(e.getMessage());
        }
        return isRunning;
    }

    private void processVoucherCommand() {
        view.print(MessageGuide.VOUCHER_COMMAND_OPTION);
        try {
            switch (Command.get(view.read())) {
                case CREATE:
                    invokeCreateVoucher();
                    break;
                case FIND:
                    invokeFindVoucher();
                    break;
                case LIST:
                    invokeFindVouchers();
                    break;
                case DELETE:
                    invokeDeleteVoucher();
                    break;
                case DELETE_ALL:
                    invokeDeleteVouchers();
                    break;
            }
        } catch (CommandException e) {
            // Nothing
        } catch (VoucherException | IllegalArgumentException e) {
            view.print(e.getMessage());
        }
    }

    private void invokeCreateVoucher() {
        view.print(MessageGuide.REQUIRE_VOUCHER_TYPE);
        String voucherType = view.read();

        view.print(MessageGuide.REQUIRE_VOUCHER_DISCOUNT_AMOUNT);
        long voucherAmount;
        try {
            voucherAmount = Long.parseLong(view.read());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Please enter a number.");
        }

        switch (voucherType) {
            case "1":
                voucherType = "FIXED";
                break;
            case "2":
                voucherType = "PERCENT";
                break;
            default:
                view.print(voucherType + " is an Invalid Voucher Type.");
                return;
        }

        voucherController.createVoucher(voucherAmount, voucherType, LocalDateTime.now(), LocalDateTime.now().plusDays(7));
    }

    private void invokeFindVoucher() {
        view.print(MessageGuide.VOUCHER_FIND_COMMAND_OPTION);

        switch (view.read()) {
            case "1":
                view.print(MessageGuide.REQUIRE_VOUCHER_ID);
                view.print(voucherController.findVoucherById(UUID.fromString(view.read())));
                break;
            case "2":
                view.print(MessageGuide.REQUIRE_CUSTOMER_ID);
                voucherController.findVouchersByOwner(UUID.fromString(view.read()))
                        .forEach(view::print);
                break;
        }
    }

    private void invokeFindVouchers() {
        voucherController.findVouchers()
                .forEach(view::print);
    }

    private void invokeDeleteVoucher() {
        view.print(MessageGuide.REQUIRE_VOUCHER_ID);

        voucherController.deleteVoucher(UUID.fromString(view.read()));
        view.print(MessageGuide.SUCCESS_MESSAGE);
    }

    private void invokeDeleteVouchers() {
        view.print("Deleted " + voucherController.deleteVouchers() + " vouchers.");
    }

    private void processCustomerCommand() {
        view.print(MessageGuide.CUSTOMER_COMMAND_OPTION);
        try {
            switch (Command.get(view.read())) {
                case CREATE:
                    invokeCreateCustomer();
                    break;
                case FIND:
                    invokeFindCustomer();
                    break;
                case LIST:
                    invokeFindCustomers();
                    break;
                case BLACKLIST:
                    invokeFindBlacklist();
                    break;
                case DELETE:
                    invokeDeleteCustomer();
                    break;
                case DELETE_ALL:
                    invokeDeleteCustomers();
                    break;
            }
        } catch (CommandException e) {
            // Nothing
        } catch (CustomerException | IllegalArgumentException e) {
            view.print(e.getMessage());
        }
    }

    private void invokeCreateCustomer() {
        view.print(MessageGuide.REQUIRE_CUSTOMER_NAME);
        String name = view.read();

        view.print(MessageGuide.REQUIRE_CUSTOMER_EMAIL);
        String email = view.read();

        customerController.createCustomer(name, email);
        view.print(MessageGuide.SUCCESS_MESSAGE);
    }

    private void invokeFindCustomer() {
        view.print(MessageGuide.CUSTOMER_FIND_COMMAND_OPTION);

        switch (view.read()) {
            case "1":
                view.print(MessageGuide.REQUIRE_CUSTOMER_ID);
                view.print(customerController.findCustomerById(UUID.fromString(view.read())));
                break;
            case "2":
                view.print(MessageGuide.REQUIRE_CUSTOMER_NAME);
                view.print(customerController.findCustomerByName(view.read()));
                break;
            case "3":
                view.print(MessageGuide.REQUIRE_CUSTOMER_EMAIL);
                view.print(customerController.findCustomerByEmail(view.read()));
                break;
        }
    }

    private void invokeFindCustomers() {
        customerController.findCustomers()
                .forEach(view::print);
    }

    private void invokeFindBlacklist() {
        customerController.findBlacklist()
                .forEach(view::print);
    }

    private void invokeDeleteCustomer() {
        view.print(MessageGuide.REQUIRE_CUSTOMER_ID);

        customerController.deleteCustomer(UUID.fromString(view.read()));
        view.print(MessageGuide.SUCCESS_MESSAGE);
    }

    private void invokeDeleteCustomers() {
        view.print("Deleted " + customerController.deleteCustomers() + " customers.");
    }

    private void processWalletCommand() {
        view.print(MessageGuide.WALLET_COMMAND_OPTION);

        try {
            switch (Command.get(view.read())) {
                case ALLOCATE:
                    invokeAllocateVoucherToWallet();
                    break;
                case SHOW:
                    invokeShowCustomerWallet();
                    break;
                case FIND:
                    invokeFindCustomerHavingVoucher();
                    break;
                case DELETE:
                    invokeRemoveVoucherFromWallet();
                    break;
                case DELETE_ALL:
                    invokeRemoveAllVouchersFromWallet();
                    break;
            }
        } catch (CommandException e) {
            // Nothing
        } catch (CustomerException | VoucherException | IllegalArgumentException e) {
            view.print(e.getMessage());
        }
    }

    private void invokeAllocateVoucherToWallet() {
        view.print(MessageGuide.REQUIRE_VOUCHER_ID);
        Voucher voucher = voucherController.findVoucherById(UUID.fromString(view.read()));

        view.print(MessageGuide.REQUIRE_CUSTOMER_ID);
        Customer customer = customerController.findCustomerById(UUID.fromString(view.read()));

        voucherController.allocateVoucher(voucher.getVoucherId(), customer.getCustomerId());
        view.print(MessageGuide.SUCCESS_MESSAGE);
    }

    private void invokeFindCustomerHavingVoucher() {
        view.print(MessageGuide.REQUIRE_VOUCHER_ID);
        Voucher voucher = voucherController.findVoucherById(UUID.fromString(view.read()));
        Customer customer = customerController.findCustomerById(voucher.getOwnerId());

        view.print(customer);
    }

    private void invokeShowCustomerWallet() {
        view.print(MessageGuide.REQUIRE_CUSTOMER_ID);
        Customer customer = customerController.findCustomerById(UUID.fromString(view.read()));

        voucherController.findVouchers().stream()
                .filter(voucher -> customer.getCustomerId().equals(voucher.getOwnerId()))
                .forEach(view::print);
    }

    private void invokeRemoveVoucherFromWallet() {
        view.print(MessageGuide.REQUIRE_VOUCHER_ID);
        Voucher voucher = voucherController.findVoucherById(UUID.fromString(view.read()));

        voucherController.detachOwnerFromVoucher(voucher.getVoucherId());
        view.print(MessageGuide.SUCCESS_MESSAGE);
    }

    private void invokeRemoveAllVouchersFromWallet() {
        view.print(MessageGuide.REQUIRE_CUSTOMER_ID);
        Customer customer = customerController.findCustomerById(UUID.fromString(view.read()));

        voucherController.findVouchersByOwner(customer.getCustomerId())
                .forEach(voucher -> voucherController.detachOwnerFromVoucher(voucher.getVoucherId()));

        view.print(MessageGuide.SUCCESS_MESSAGE);
    }
}
