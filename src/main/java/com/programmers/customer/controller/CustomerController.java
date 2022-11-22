package com.programmers.customer.controller;

import com.programmers.customer.Customer;
import com.programmers.customer.service.CustomerService;
import com.programmers.view.View;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

import static com.programmers.message.ErrorMessage.ERROR_INPUT_MESSAGE;
import static com.programmers.message.Message.*;

@Component
public class CustomerController {
    private final CustomerService customerService;
    private final View view;

    public CustomerController(CustomerService customerService, View view) {
        this.customerService = customerService;
        this.view = view;
    }

    public void findAllCustomers() {
        List<Customer> customers = customerService.findAll();

        view.printList(customers);
    }

    public void join() {
        view.printMessage(CUSTOMER_NAME);
        String name = view.getUserCommand();

        view.printMessage(CUSTOMER_EMAIL);
        String email = view.getUserCommand();

        validateForJoin(name, email);

        customerService.join(name, email);
    }

    public void findVoucherOwner() {
        view.printMessage(VOUCHER_ID);
        String voucherId = view.getUserCommand();
        UUID voucherUUID = UUID.fromString(voucherId);

        Customer customer = customerService.findCustomerByVoucherId(voucherUUID);
        view.printCustomer(customer);
    }


    private void validateForJoin(String name, String email) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(email)) {
            throw new RuntimeException(ERROR_INPUT_MESSAGE.getMessage());
        }
    }

}
