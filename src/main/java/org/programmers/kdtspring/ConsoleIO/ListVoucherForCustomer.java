package org.programmers.kdtspring.ConsoleIO;

import org.programmers.kdtspring.entity.user.Customer;
import org.programmers.kdtspring.service.CustomerService;

import java.util.Optional;

public class ListVoucherForCustomer implements CommandStrategy {

    private final CustomerService customerService;
    private final Input input;
    private final Output output;

    public ListVoucherForCustomer(Input input, Output output, CustomerService customerService) {
        this.customerService = customerService;
        this.input = input;
        this.output = output;
    }

    @Override
    public void runCommand() {
        String email = input.inputEmail();
        Optional<Customer> customer = customerService.getCustomerByEmail(email);

        output.vouchersBelongToCustomer(customer.get().getCustomerId());
    }
}