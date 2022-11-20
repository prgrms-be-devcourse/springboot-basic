package com.programmers.kwonjoosung.springbootbasicjoosung.controller.customer;

import com.programmers.kwonjoosung.springbootbasicjoosung.console.Console;
import com.programmers.kwonjoosung.springbootbasicjoosung.controller.Command;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.service.CustomerService;
import com.programmers.kwonjoosung.springbootbasicjoosung.service.WalletService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.kwonjoosung.springbootbasicjoosung.console.message.CustomerResponseMessage.*;

@Component
public class CustomerController {
    private final CustomerService customerService;
    private final WalletService walletService;
    private final Console console;

    public CustomerController(CustomerService customerService, WalletService walletService, Console console) {
        this.customerService = customerService;
        this.walletService = walletService;
        this.console = console;
    }

    public void execute(Command command) {
        switch (command) {
            case CREATE -> createCustomer();
            case READ -> findCustomer();
            case UPDATE -> updateCustomer();
            case DELETE -> deleteCustomer();
            case FIND -> findOwnedVouchers();
            case LIST -> listCustomer();
        }
    }

    private void findOwnedVouchers() {
        UUID customerId = console.getCustomerId();
        List<Voucher> vouchers = walletService.findVouchersByCustomerId(customerId);
        if (vouchers.isEmpty()) console.printMessage(NOT_FOUND_OWNED_VOUCHER);
        vouchers.forEach(console::printVoucher);
    }

    private void createCustomer() {
        String name = console.getCustomerName();
        Optional<Customer> customer = customerService.createCustomer(name);
        customer.ifPresentOrElse(console::printCustomer, () -> console.printMessage(CREATE_CUSTOMER_FAIL));
    }

    private void findCustomer() {
        UUID customerId = console.getCustomerId();
        Optional<Customer> customer = customerService.findCustomerByCustomerId(customerId);
        customer.ifPresentOrElse(console::printCustomer, () -> console.printError(NOT_FOUND_CUSTOMER_ERROR));
    }

    private void updateCustomer() {
        UUID customerId = console.getCustomerId();
        String name = console.getCustomerName();
        boolean result = customerService.updateCustomer(customerId, name);
        if (result) console.printMessage(UPDATE_CUSTOMER_SUCCESS);
        else console.printError(NOT_FOUND_CUSTOMER_ERROR);
    }

    private void deleteCustomer() {
        UUID customerId = console.getCustomerId();
        boolean result = customerService.deleteCustomer(customerId);
        if (result) console.printMessage(DELETE_CUSTOMER_SUCCESS);
        else console.printError(NOT_FOUND_CUSTOMER_ERROR);
    }

    private void listCustomer() {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) console.printMessage(NOT_FOUND_CUSTOMER_ERROR);
        customers.forEach(console::printCustomer);
    }


}
