package com.programmers.customer.controller;

import com.programmers.customer.Customer;
import com.programmers.customer.service.CustomerService;
import com.programmers.view.View;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.programmers.message.ErrorMessage.ERROR_INPUT_MESSAGE;
import static com.programmers.message.Message.CUSTOMER_EMAIL;
import static com.programmers.message.Message.CUSTOMER_NAME;

@Controller
public class CustomerController {
    private final CustomerService customerService;
    private final View view;

    public CustomerController(CustomerService customerService, View view) {
        this.customerService = customerService;
        this.view = view;
    }

    public void findAllCustomers() {
        List<Customer> customers = customerService.findAll();

        for (Customer customer : customers) {
            view.printCustomer(customer);
        }
    }

    public void join() {
        view.printMessage(CUSTOMER_NAME.getMessage());
        String name = view.getUserCommand();

        view.printMessage(CUSTOMER_EMAIL.getMessage());
        String email = view.getUserCommand();

        validateInput(name, email);

        customerService.join(name, email);
    }

    private void validateInput(String name, String email) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(email)) {
            throw new RuntimeException(ERROR_INPUT_MESSAGE.getMessage());
        }
    }

}
