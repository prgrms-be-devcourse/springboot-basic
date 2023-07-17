package com.prgms.voucher.voucherproject.App;

import com.prgms.voucher.voucherproject.domain.customer.Customer;
import com.prgms.voucher.voucherproject.domain.customer.CustomerType;
import com.prgms.voucher.voucherproject.exception.DuplicateCustomerException;
import com.prgms.voucher.voucherproject.exception.NotFoundCustomerException;
import com.prgms.voucher.voucherproject.io.Console;
import com.prgms.voucher.voucherproject.io.Constant;
import com.prgms.voucher.voucherproject.service.CustomerService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerApp {

    private final Console console = new Console();
    private final CustomerService customerService;

    public CustomerApp(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void runCustomerProgram() throws Exception {
        boolean isRunning = true;

        while (isRunning) {
            CustomerType customerMenu = console.inputCustomerMenu();

            if (customerMenu == null) continue;

            switch (customerMenu) {
                case CREATE -> createCustomer();
                case LIST -> listCustomers();
                case FIND -> findCustomer();
                case DELETE -> deleteCustomer();
                case EXIT -> {
                    isRunning = false;
                    console.printMessage("Customer " + Constant.PROGRAM_END, true);
                }
            }
        }
    }

    private void createCustomer() {
        Customer customer = console.inputCreateCustomer();

        if (customer == null) return;

        try {
            customerService.createCustomer(customer);
        } catch (DuplicateKeyException e) {
            console.printMessage(Constant.EXITS_EMAIL, true);
        } catch (DuplicateCustomerException e) {
            console.printMessage(e.getLocalizedMessage(), true);
        }
    }

    private void listCustomers() {
        List<Customer> customers = customerService.getCustomerList();
        console.printCustomerList(customers);
    }

    private void findCustomer() {
        String email = console.inputEmail();

        if (email.equals("WrongEmail")) {
            console.printMessage(Constant.WRONG_EMAIL, true);
            return;
        }

        Optional<Customer> customer = customerService.findByEmail(email);

        if (customer.isEmpty()) console.printMessage(Constant.NOT_EXITS_CUSTOMER, true);

        customer.ifPresent(console::printCustomerInfo);
    }

    private void deleteCustomer() {
        String email = console.inputEmail();

        if (email.equals("WrongEmail")) {
            console.printMessage(Constant.WRONG_EMAIL, true);
            return;
        }

        try{
            customerService.deleteByEmail(email);
        } catch (NotFoundCustomerException e) {
            console.printMessage(e.getLocalizedMessage(), true);
        }

    }

}
