package com.programmers.voucher.domain.customer.controller;

import com.programmers.voucher.domain.customer.dto.CustomerCreateRequest;
import com.programmers.voucher.domain.customer.dto.CustomerResponse;
import com.programmers.voucher.domain.customer.dto.CustomerUpdateRequest;
import com.programmers.voucher.domain.customer.service.CustomerService;
import com.programmers.voucher.view.Input;
import com.programmers.voucher.view.Output;
import com.programmers.voucher.view.command.CustomerCommand;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class CustomerController implements Runnable {
    private final Input input;
    private final Output output;
    private final CustomerService customerService;

    public CustomerController(Input input, Output output, CustomerService customerService) {
        this.input = input;
        this.output = output;
        this.customerService = customerService;
    }

    @Override
    public void run() {
        output.displayCustomerCommands();
        CustomerCommand command = input.readCustomerCommand();

        switch (command) {
            case CREATE -> {
                CustomerResponse customer = createCustomer();
                output.displayCustomer(customer);
            }
            case READ_ALL -> getAllCustomers().forEach(output::displayCustomer);
            case READ -> output.displayCustomer(getCustomer());
            case UPDATE -> output.displayCustomer(updateCustomer());
            case DELETE -> deleteCustomer();
        }
    }

    private CustomerResponse createCustomer() {
        String nickname = input.readNickname();
        CustomerCreateRequest customerCreateRequest = CustomerCreateRequest.of(nickname);

        return customerService.createCustomer(customerCreateRequest);
    }

    private List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    private CustomerResponse getCustomer() {
        UUID customerId = input.readUUID();
        return customerService.getCustomer(customerId);
    }

    private CustomerResponse updateCustomer() {
        UUID customerId = input.readUUID();
        String nickname = input.readNickname();
        CustomerUpdateRequest customerUpdateRequest = CustomerUpdateRequest.of(nickname);

        return customerService.updateCustomer(customerId, customerUpdateRequest);
    }

    private void deleteCustomer() {
        UUID customerId = input.readUUID();
        customerService.deleteCustomer(customerId);
    }
}
