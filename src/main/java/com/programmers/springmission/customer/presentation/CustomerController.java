package com.programmers.springmission.customer.presentation;

import com.programmers.springmission.customer.application.CustomerService;
import com.programmers.springmission.customer.domain.enums.FindType;
import com.programmers.springmission.customer.presentation.request.CustomerCreateRequest;
import com.programmers.springmission.customer.presentation.request.CustomerUpdateRequest;
import com.programmers.springmission.customer.presentation.response.CustomerResponse;
import com.programmers.springmission.customer.presentation.response.WalletResponse;
import com.programmers.springmission.view.Console;
import com.programmers.springmission.view.CrudType;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class CustomerController {

    private final Console console;
    private final CustomerService customerService;

    public CustomerController(Console console, CustomerService customerService) {
        this.console = console;
        this.customerService = customerService;
    }

    public void run() {
        console.outputCustomerMenu();
        CrudType inputValue = CrudType.of(console.input());

        switch (inputValue) {
            case CREATE -> {
                CustomerResponse customerResponse = createCustomer();
                console.outputCustomerCreate(customerResponse);
            }
            case FIND_ONE -> {
                findOneCustomer();
            }
            case FIND_ALL -> {
                List<CustomerResponse> customerResponses = customerService.findAllCustomer();
                console.outputCustomerFindAll(customerResponses);
            }
            case UPDATE -> {
                CustomerResponse customerResponse = updateName();
                console.outputCustomerUpdate(customerResponse);
            }
            case DELETE_BY_ID -> {
                deleteCustomer();
                console.outputCustomerDeleteById();
            }
            case DELETE_ALL ->  {
                customerService.deleteAllCustomer();
                console.outputCustomerDeleteAll();
            }
            case WALLET -> {
                List<WalletResponse> walletResponses = findWallet();
                console.outputCustomerWallet(walletResponses);
            }
        }
    }

    private CustomerResponse createCustomer() {
        String name = console.inputCustomerName().trim();
        String email = console.inputCustomerEmail().trim();

        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(name, email);
        return customerService.createCustomer(customerCreateRequest);
    }

    private void findOneCustomer() {
        FindType findType = FindType.of(console.inputCustomerFindOption());

        switch (findType) {
            case CUSTOMER_ID -> {
                CustomerResponse customerResponse = findByIdCustomer();
                console.outputCustomerFindOne(customerResponse);
            }
            case CUSTOMER_EMAIL -> {
                CustomerResponse customerResponse = findByEmailCustomer();
                console.outputCustomerFindOne(customerResponse);
            }
        };
    }

    private CustomerResponse findByIdCustomer() {
        UUID inputCustomerId = UUID.fromString(console.inputCustomerId());
        return customerService.findByIdCustomer(inputCustomerId);
    }

    private CustomerResponse findByEmailCustomer() {
        String inputCustomerEmail = console.inputCustomerEmail();
        return customerService.findByEmailCustomer(inputCustomerEmail);
    }

    private CustomerResponse updateName() {
        UUID inputCustomerId = UUID.fromString(console.inputCustomerId());
        String inputCustomerName = console.inputCustomerName();

        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(inputCustomerName);
        return customerService.updateName(inputCustomerId, customerUpdateRequest);
    }

    private void deleteCustomer() {
        UUID inputCustomerId = UUID.fromString(console.inputVoucherId());
        customerService.deleteCustomer(inputCustomerId);
    }

    private List<WalletResponse> findWallet() {
        UUID inputCustomerId = UUID.fromString(console.inputCustomerId());
        return customerService.findWallet(inputCustomerId);
    }
}
