package com.prgrms.voucher_manage.console;

import com.prgrms.voucher_manage.console.io.IOManager;
import com.prgrms.voucher_manage.console.io.InputUtil;
import com.prgrms.voucher_manage.console.io.OutputUtil;
import com.prgrms.voucher_manage.domain.customer.controller.CustomerConsoleController;
import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.customer.entity.CustomerType;
import com.prgrms.voucher_manage.domain.voucher.controller.VoucherConsoleController;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;
import com.prgrms.voucher_manage.domain.wallet.controller.WalletController;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.prgrms.voucher_manage.console.ConsoleMessage.*;
import static com.prgrms.voucher_manage.console.MenuType.EXIT;
import static com.prgrms.voucher_manage.console.MenuType.matchMenuType;
import static com.prgrms.voucher_manage.exception.ErrorMessage.INVALID_COMMAND_INPUT;

@Component
@RequiredArgsConstructor
public class ConsoleManager implements ApplicationRunner {
    private final IOManager ioManager;
    private final OutputUtil outputUtil;
    private final InputUtil inputUtil;
    private final VoucherConsoleController voucherController;
    private final CustomerConsoleController customerController;
    private final WalletController walletController;
    private static final Logger logger = LoggerFactory.getLogger(ConsoleManager.class);

    @Override
    public void run(ApplicationArguments args) {
        MenuType menuType = null;
        do {
            try {
                outputUtil.printMenu();
                menuType = matchMenuType(inputUtil.getStringInput());
                selectMenu(menuType);
            } catch (Exception e) {
                outputUtil.printMessage(e.getMessage());
                logger.error(e.getMessage());
            }

        } while (menuType != EXIT);
    }

    public void selectMenu(MenuType menuType) throws Exception {
        if (menuType == null) {
            throw new IllegalArgumentException(INVALID_COMMAND_INPUT.getMessage());
        }

        switch (menuType) {
            case SAVE_VOUCHER -> saveVoucher();
            case VOUCHER_LIST -> printVouchers(voucherController.getVouchers());
            case FIND_VOUCHER -> findVoucher();
            case UPDATE_VOUCHER -> updateVoucher();
            case DELETE_VOUCHER -> deleteVoucher();

            case SAVE_CUSTOMER -> saveCustomer();
            case BLACK_CUSTOMERS -> printCustomers(customerController.getBlackCustomers());
            case ALL_CUSTOMERS -> printCustomers(customerController.getAllCustomers());
            case UPDATE_CUSTOMER -> updateCustomer();
            case FIND_CUSTOMER -> findCustomer();

            case SAVE_WALLET -> saveWallet();
            case FIND_WALLET_VOUCHERS -> findWalletVouchers();
            case FIND_WALLET_CUSTOMERS -> findWalletCustomers();
            case DELETE_WALLET -> deleteWallet();

        }
    }

    public void saveWallet() {
        UUID customerId = ioManager.getUUID(WALLET_SAVE_CUSTOMER_ID);
        UUID voucherId = ioManager.getUUID(WALLET_SAVE_VOUCHER_ID);
        walletController.saveWallet(customerId, voucherId);
    }

    public void findWalletVouchers() {
        UUID customerId = ioManager.getUUID(WALLET_FIND_CUSTOMER_ID);
        List<Voucher> vouchers = walletController.findVouchers(customerId);
        printVouchers(vouchers);
    }

    public void findWalletCustomers() {
        UUID voucherId = ioManager.getUUID(WALLET_FIND_VOUCHER_ID);
        List<Customer> customers = walletController.findCustomers(voucherId);
        printCustomers(customers);
    }

    public void deleteWallet() {
        UUID customerId = ioManager.getUUID(WALLET_DELETE_CUSTOMER_ID);
        UUID voucherId = ioManager.getUUID(WALLET_DELETE_VOUCHER_ID);
        walletController.deleteWallet(customerId, voucherId);
    }

    public void saveVoucher() throws Exception {
        VoucherType voucherType = VoucherType.matchVoucherType(ioManager.getVoucherType());
        if (voucherType == null) {
            throw new IllegalArgumentException(INVALID_COMMAND_INPUT.getMessage());
        }
        switch (voucherType) {
            case FIXED -> outputUtil.requestDiscountPriceInfo();
            case PERCENT -> outputUtil.requestDiscountPercentInfo();
        }
        Long discountAmount = inputUtil.getLongInput();
        voucherController.saveVoucher(voucherType, discountAmount);
    }

    private void findVoucher() {
        UUID voucherId = ioManager.getUUID(VOUCHER_FIND_ID);
        Voucher voucher = voucherController.findVoucher(voucherId);
        switch (voucher.getType()) {
            case FIXED -> outputUtil.printFixedVoucherInfo(voucher);
            case PERCENT -> outputUtil.printPercentVoucherInfo(voucher);
        }
    }

    public void updateVoucher() throws Exception {
        UUID voucherId = ioManager.getUUID(VOUCHER_UPDATE_ID);
        Long discountAmount = ioManager.getLong(VOUCHER_UPDATE_PRICE);
        voucherController.updateVoucher(voucherId, discountAmount);
    }

    public void deleteVoucher() {
        UUID voucherId = ioManager.getUUID(VOUCHER_DELETE_ID);
        voucherController.deleteVoucher(voucherId);
    }

    private CustomerType getCustomerType() throws IOException {
        CustomerType customerType = CustomerType.matchTypeByString(inputUtil.getStringInput());
        if (customerType == null) {
            throw new IllegalArgumentException(INVALID_COMMAND_INPUT.getMessage());
        }
        return customerType;
    }

    private void printVouchers(List<Voucher> vouchers) {
        vouchers.forEach(voucher -> {
            switch (voucher.getType()) {
                case FIXED -> outputUtil.printFixedVoucherInfo(voucher);
                case PERCENT -> outputUtil.printPercentVoucherInfo(voucher);
            }
        });
    }

    public void saveCustomer() throws IOException {
        outputUtil.printCustomerSelect();
        CustomerType type = getCustomerType();
        String name = ioManager.getString(CUSTOMER_SAVE_NAME);
        customerController.saveCustomer(name, type);
    }

    public void findCustomer() throws IOException {
        String name = ioManager.getString(CUSTOMER_FIND_NAME);
        Customer foundCustomer = customerController.findByName(name);
        outputUtil.printCustomerInfo(foundCustomer);
    }

    public void updateCustomer() throws Exception {
        UUID id = ioManager.getUUID(CUSTOMER_UPDATE_ID);
        Customer customer = customerController.findById(id);

        String name = ioManager.getString(CUSTOMER_UPDATE_NAME);
        customerController.update(customer.getId(), name);
    }

    private void printCustomers(List<Customer> customers) {
        customers.forEach(outputUtil::printCustomerInfo);
    }
}
