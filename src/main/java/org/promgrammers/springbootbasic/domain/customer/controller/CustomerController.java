package org.promgrammers.springbootbasic.domain.customer.controller;

import org.promgrammers.springbootbasic.domain.customer.dto.request.CreateCustomerRequest;
import org.promgrammers.springbootbasic.domain.customer.dto.request.UpdateCustomerRequest;
import org.promgrammers.springbootbasic.domain.customer.dto.response.CustomerResponse;
import org.promgrammers.springbootbasic.domain.customer.dto.response.CustomersResponse;
import org.promgrammers.springbootbasic.domain.customer.model.CustomerType;
import org.promgrammers.springbootbasic.domain.customer.model.DeleteCustomerType;
import org.promgrammers.springbootbasic.domain.customer.model.FindCustomerType;
import org.promgrammers.springbootbasic.domain.customer.service.BlackCustomerService;
import org.promgrammers.springbootbasic.domain.customer.service.CustomerService;
import org.promgrammers.springbootbasic.view.Console;
import org.promgrammers.springbootbasic.view.CrudMenu;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class CustomerController {

    private final Console console;
    private final CustomerService customerService;
    private final BlackCustomerService blackCustomerService;

    public CustomerController(CustomerService customerService, Console console, BlackCustomerService blackCustomerService) {
        this.customerService = customerService;
        this.console = console;
        this.blackCustomerService = blackCustomerService;
    }

    public void execute() {
        String inputCommand = console.customerCommandGuide();
        CrudMenu crudMenu = CrudMenu.from(inputCommand);

        switch (crudMenu) {
            case CREATE -> {
                CustomerResponse customer = create();
                console.print("고객 등록이 완료되었습니다. : \n   " + customer.customerOutput());
            }
            case FIND_ALL -> {
                CustomersResponse customerList = findAll();
                console.print(customerList.allCustomerOutput());
            }
            case FIND_ONE -> {
                findByType();
            }
            case UPDATE -> {
                CustomerResponse updatedCustomer = update();
                console.print(updatedCustomer.customerOutput());
            }
            case DELETE -> {
                deleteType();
                console.print("삭제 되었습니다.");
            }
        }
    }

    private void deleteType() {
        String inputType = console.askForCustomerDeleteType();
        DeleteCustomerType deleteType = DeleteCustomerType.from(inputType);

        switch (deleteType) {
            case ID -> deleteCustomerById();
            case ALL -> deleteAll();
        }
    }

    private CustomerResponse create() {
        console.print("고객 이름을 입력해 주세요.");
        String inputUsername = console.input();
        CreateCustomerRequest customerRequest = new CreateCustomerRequest(inputUsername);
        return customerService.createCustomer(customerRequest);
    }

    private CustomerResponse update() {
        String requestId = console.askForCustomerId();
        UUID customerId = UUID.fromString(requestId);

        console.print("수정 할 이름을 적어주세요.");
        String requestUsername = console.input();

        CustomerType customerType = CustomerType.WHITE;

        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest(customerId, requestUsername, customerType);
        return customerService.updateCustomer(updateCustomerRequest);
    }

    private void findByType() {
        String inputType = console.askForCustomerFindType();
        FindCustomerType findType = FindCustomerType.from(inputType);

        switch (findType) {
            case ID -> findCustomerById();

            case USERNAME -> findCustomerByUsername();

            case BLACKLIST -> findBlackListCustomer();
        }
    }

    private CustomerResponse findCustomerById() {
        String requestId = console.askForCustomerId();
        UUID voucherId = UUID.fromString(requestId);

        CustomerResponse customer = customerService.findCustomerById(voucherId);
        console.print(customer.customerOutput());

        return customer;
    }

    private CustomerResponse findCustomerByUsername() {
        console.print("조회할 고객 이름을 입력하세요");
        String inputUsername = console.input();

        CustomerResponse customer = customerService.findCustomerByUsername(inputUsername);
        console.print(customer.customerOutput());

        return customer;
    }

    private CustomersResponse findBlackListCustomer() {
        CustomersResponse blackList = blackCustomerService.findAllByCustomerType(CustomerType.BLACK);
        console.print(blackList.blackTypeOutput());

        return blackList;
    }

    private CustomersResponse findAll() {
        return customerService.findAllCustomers();
    }

    public void deleteAll() {
        customerService.deleteAllCustomers();
        console.print("모든 고객이 삭제 되었습니다.");
    }

    private void deleteCustomerById() {
        String requestId = console.askForCustomerId();
        UUID voucherId = UUID.fromString(requestId);

        customerService.deleteById(voucherId);
    }
}
