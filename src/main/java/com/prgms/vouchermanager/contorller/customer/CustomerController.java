package com.prgms.vouchermanager.contorller.customer;

import com.prgms.vouchermanager.domain.customer.Customer;
import com.prgms.vouchermanager.service.customer.CustomerService;
import com.prgms.vouchermanager.util.io.ConsoleInput;
import org.springframework.stereotype.Controller;

import java.util.List;

import static com.prgms.vouchermanager.contorller.customer.CustomerMenuType.*;

@Controller
public class CustomerController {

    private final CustomerService customerService;
    private final ConsoleInput consoleInput;

    public CustomerController(CustomerService customerService, ConsoleInput consoleInput) {
        this.customerService = customerService;
        this.consoleInput = consoleInput;
    }

    public void run() {
        int menu = 0;
        try {
            menu = consoleInput.inputCustomerMenu();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return;
        }
        if (menu == BLACK_LIST.getNumber()) {

            List<Customer> blackList = customerService .getBlackList();

            blackList.
                    forEach(customer -> System.out.println(customer.toString()));
        }
    }
}
