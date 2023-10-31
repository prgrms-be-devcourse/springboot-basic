package com.programmers.vouchermanagement.consoleapp.menu;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.vouchermanagement.consoleapp.io.ConsoleManager;
import com.programmers.vouchermanagement.customer.controller.CustomerController;
import com.programmers.vouchermanagement.customer.dto.UpdateCustomerRequest;
import com.programmers.vouchermanagement.voucher.controller.VoucherController;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.UpdateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherCustomerRequest;

@Component
public class MenuHandler {
    private final ConsoleManager consoleManager;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    public MenuHandler(ConsoleManager consoleManager, VoucherController voucherController, CustomerController customerController) {
        this.consoleManager = consoleManager;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    public void handleMenu(Menu menu) {
        try {
            executeMenu(menu);
        } catch (RuntimeException e) {
            consoleManager.printException(e);
        }
    }

    private void executeMenu(Menu menu) {
        switch (menu) {
            case EXIT -> consoleManager.printExit();
            case INCORRECT_MENU -> consoleManager.printIncorrectMenu();
            case VOUCHER -> executeVoucherMenu();
            case CUSTOMER -> executeCustomerMenu();
        }
    }

    private void executeVoucherMenu() {
        VoucherMenu voucherMenu = consoleManager.selectVoucherMenu();
        switch (voucherMenu) {
            case CREATE -> {
                CreateVoucherRequest request = consoleManager.instructCreateVoucher();
                voucherController.create(request);
            }
            case LIST -> voucherController.readAllVouchers();
            case SEARCH -> {
                UUID voucherId = consoleManager.instructFindVoucher();
                voucherController.findById(voucherId);
            }
            case UPDATE -> {
                UpdateVoucherRequest request = consoleManager.instructUpdateVoucher();
                voucherController.update(request);
            }
            case DELETE -> {
                UUID voucherId = consoleManager.instructFindVoucher();
                voucherController.deleteById(voucherId);
            }
            case GRANT -> {
                VoucherCustomerRequest request = consoleManager.instructRequestVoucherCustomer();
                voucherController.grantToCustomer(request);
            }
            case SEARCH_OWNER -> {
                UUID voucherId = consoleManager.instructFindVoucher();
                customerController.findVoucherOwner(voucherId);
            }
            case INCORRECT_MENU -> consoleManager.printIncorrectMenu();
        }
    }

    private void executeCustomerMenu() {
        CustomerMenu customerMenu = consoleManager.selectCustomerMenu();
        switch (customerMenu) {
            case CREATE -> {
                String name = consoleManager.instructCreateCustomer();
                customerController.create(name);
            }
            case LIST -> customerController.readAllCustomers();
            case SEARCH -> {
                UUID customerId = consoleManager.instructFindCustomer();
                customerController.findById(customerId);
            }
            case UPDATE -> {
                UpdateCustomerRequest updateCustomerRequest = consoleManager.instructUpdateCustomer();
                customerController.update(updateCustomerRequest);
            }
            case BLACKLIST -> customerController.readBlacklist();
            case DELETE -> {
                UUID customerId = consoleManager.instructFindCustomer();
                customerController.deleteById(customerId);
            }
            case SEARCH_VOUCHERS -> {
                UUID customerId = consoleManager.instructFindCustomer();
                voucherController.searchOwnedVouchers(customerId);
            }
            case REMOVE_VOUCHER -> {
                VoucherCustomerRequest request = consoleManager.instructRequestVoucherCustomer();
                voucherController.removeVoucherFromCustomer(request);
            }
            case INCORRECT_MENU -> consoleManager.printIncorrectMenu();
        }
    }
}
