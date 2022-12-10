package org.prgrms.java.common;

import org.prgrms.java.controller.CustomerController;
import org.prgrms.java.controller.VoucherController;
import org.prgrms.java.domain.customer.Customer;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.exception.badrequest.CustomerBadRequestException;
import org.prgrms.java.exception.badrequest.VoucherBadRequestException;
import org.prgrms.java.exception.notfound.CommandNotFoundException;
import org.prgrms.java.exception.notfound.CustomerNotFoundException;
import org.prgrms.java.exception.notfound.VoucherNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Profile("console")
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
        view.print(MessageGuide.COMMAND_OPTION);
        try {
            switch (MenuCommand.get(view.read())) {
                case EXIT -> {
                    view.print("Terminates the program...");
                    return false;
                }
                case VOUCHER -> processVoucherCommand();
                case CUSTOMER -> processCustomerCommand();
                case WALLET -> processWalletCommand();
            }
            return true;
        } catch (CommandNotFoundException e) {
            view.print(e.getMessage());
            logger.warn(e.getMessage());
        }
        return true;
    }

    private void processVoucherCommand() {
        view.print(MessageGuide.VOUCHER_COMMAND_OPTION);
        try {
            switch (HandlingCommand.get(view.read())) {
                case CREATE -> invokeCreateVoucher();
                case FIND -> invokeFindVoucher();
                case LIST -> invokeFindVouchers();
                case DELETE -> invokeDeleteVoucher();
                case DELETE_ALL -> invokeDeleteVouchers();
            }
        } catch (VoucherNotFoundException | VoucherBadRequestException e) {
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
            case "1" -> voucherType = "FIXED";
            case "2" -> voucherType = "PERCENT";
            default -> view.print(voucherType + " is an Invalid Voucher Type.");
        }

        voucherController.createVoucher(voucherAmount, voucherType, LocalDateTime.now().plusDays(7));
    }

    private void invokeFindVoucher() {
        view.print(MessageGuide.VOUCHER_FIND_COMMAND_OPTION);

        switch (view.read()) {
            case "1" -> {
                view.print(MessageGuide.REQUIRE_VOUCHER_ID);
                view.print(voucherController.findVoucherById(view.read()));
            }
            case "2" -> {
                view.print(MessageGuide.REQUIRE_CUSTOMER_ID);
                voucherController.findVouchersByOwner(view.read())
                        .forEach(view::print);
            }
        }
    }

    private void invokeFindVouchers() {
        voucherController.findVouchers()
                .forEach(view::print);
    }

    private void invokeDeleteVoucher() {
        view.print(MessageGuide.REQUIRE_VOUCHER_ID);

        voucherController.deleteVoucher(view.read());
        view.print(MessageGuide.SUCCESS_MESSAGE);
    }

    private void invokeDeleteVouchers() {
        view.print("Deleted All vouchers.");
    }

    private void processCustomerCommand() {
        view.print(MessageGuide.CUSTOMER_COMMAND_OPTION);
        try {
            switch (HandlingCommand.get(view.read())) {
                case CREATE -> invokeCreateCustomer();
                case FIND -> invokeFindCustomer();
                case LIST -> invokeFindCustomers();
                case BLACKLIST -> invokeFindBlacklist();
                case DELETE -> invokeDeleteCustomer();
                case DELETE_ALL -> invokeDeleteCustomers();
            }
        } catch (CustomerNotFoundException | CustomerBadRequestException e) {
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
            case "1" -> {
                view.print(MessageGuide.REQUIRE_CUSTOMER_ID);
                view.print(customerController.findCustomer("id", view.read()));
            }
            case "2" -> {
                view.print(MessageGuide.REQUIRE_CUSTOMER_NAME);
                view.print(customerController.findCustomer("name", view.read()));
            }
            case "3" -> {
                view.print(MessageGuide.REQUIRE_CUSTOMER_EMAIL);
                view.print(customerController.findCustomer("email", view.read()));
            }
        }
    }

    private void invokeFindCustomers() {
        customerController.findCustomers(false)
                .forEach(view::print);
    }

    private void invokeFindBlacklist() {
        customerController.findCustomers(true)
                .forEach(view::print);
    }

    private void invokeDeleteCustomer() {
        view.print(MessageGuide.REQUIRE_CUSTOMER_ID);

        customerController.deleteCustomer(UUID.fromString(view.read()));
        view.print(MessageGuide.SUCCESS_MESSAGE);
    }

    private void invokeDeleteCustomers() {
        customerController.deleteCustomers();
        view.print("Deleted All customers.");
    }

    private void processWalletCommand() {
        view.print(MessageGuide.WALLET_COMMAND_OPTION);
        try {
            switch (HandlingCommand.get(view.read())) {
                case ALLOCATE -> invokeAllocateVoucherToWallet();
                case SHOW -> invokeShowCustomerWallet();
                case FIND -> invokeFindCustomerHavingVoucher();
                case DELETE -> invokeRemoveVoucherFromWallet();
                case DELETE_ALL -> invokeRemoveAllVouchersFromWallet();
            }
        } catch (CustomerBadRequestException | CustomerNotFoundException | VoucherBadRequestException | VoucherNotFoundException e) {
            view.print(e.getMessage());
        }
    }

    private void invokeAllocateVoucherToWallet() {
        view.print(MessageGuide.REQUIRE_VOUCHER_ID);
        String voucherId = view.read();

        view.print(MessageGuide.REQUIRE_CUSTOMER_ID);
        String customerId = view.read();

        voucherController.allocateVoucher(voucherId, customerId);
        view.print(MessageGuide.SUCCESS_MESSAGE);
    }

    private void invokeFindCustomerHavingVoucher() {
        view.print(MessageGuide.REQUIRE_VOUCHER_ID);
        Voucher voucher = voucherController.findVoucherById(view.read());
        Customer customer = customerController.findCustomer("id", voucher.getOwnerId().toString());

        view.print(customer);
    }

    private void invokeShowCustomerWallet() {
        view.print(MessageGuide.REQUIRE_CUSTOMER_ID);
        UUID customerId = UUID.fromString(view.read());

        voucherController.findVouchers().stream()
                .filter(voucher -> customerId.equals(voucher.getOwnerId()))
                .forEach(view::print);
    }

    private void invokeRemoveVoucherFromWallet() {
        view.print(MessageGuide.REQUIRE_VOUCHER_ID);

        voucherController.detachOwnerFromVoucher(view.read());
        view.print(MessageGuide.SUCCESS_MESSAGE);
    }

    private void invokeRemoveAllVouchersFromWallet() {
        view.print(MessageGuide.REQUIRE_CUSTOMER_ID);
        String customerId = view.read();

        voucherController.findVouchersByOwner(customerId)
                .forEach(voucher -> voucherController.detachOwnerFromVoucher(voucher.getVoucherId().toString()));

        view.print(MessageGuide.SUCCESS_MESSAGE);
    }
}
