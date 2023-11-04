package org.prgms.kdtspringweek1.controller.consoleController;

import org.prgms.kdtspringweek1.console.ConsoleOutput;
import org.prgms.kdtspringweek1.controller.consoleController.dto.SelectFunctionTypeDto;
import org.prgms.kdtspringweek1.customer.service.dto.FindCustomerResponseDto;
import org.prgms.kdtspringweek1.customer.service.dto.UpdateCustomerRequestDto;
import org.prgms.kdtspringweek1.customer.entity.Customer;
import org.prgms.kdtspringweek1.customer.service.CustomerService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerController {
    private final ConsoleInputConverter consoleInputConverter;
    private final ConsoleOutput consoleOutput;
    private final CustomerService customerService;

    public CustomerController(ConsoleInputConverter consoleInputConverter, ConsoleOutput consoleOutput, CustomerService customerService) {
        this.consoleInputConverter = consoleInputConverter;
        this.consoleOutput = consoleOutput;
        this.customerService = customerService;
    }

    public void selectCustomerFunction(SelectFunctionTypeDto functionTypeDto) {
        switch (functionTypeDto) {
            case CREATE_CUSTOMER -> createCustomer();
            case LIST_CUSTOMERS -> getAllCustomers();
            case SEARCH_CUSTOMER -> getCustomerById();
            case UPDATE_CUSTOMER -> updateCustomerInfo();
            case DELETE_ALL_CUSTOMERS -> deleteAllCustomers();
            case DELETE_CUSTOMER -> deleteCustomer();
            case LIST_BLACK_CUSTOMERS -> getAllBlackCustomers();
        }
    }

    private void createCustomer() {
        consoleOutput.printRequestMessageForName();
        customerService.registerCustomer(Customer.createWithName(consoleInputConverter.getCustomerName()));
        consoleOutput.printSuccessToCreate();
    }

    private void getAllCustomers() {
        customerService.searchAllCustomers()
                .forEach(FindCustomerResponseDto::printCustomerInfo);
        consoleOutput.printSuccessToSearch();
    }

    private void getCustomerById() {
        consoleOutput.printRequestMessageForCustomerId();
        customerService.searchCustomerById(consoleInputConverter.getId())
                .ifPresentOrElse(
                        findCustomerResponseDto -> {
                            findCustomerResponseDto.printCustomerInfo();
                            consoleOutput.printSuccessToSearch();
                        },
                        consoleOutput::printValueNotFound
                );
    }

    private void updateCustomerInfo() {
        consoleOutput.printRequestMessageForCustomerId();
        UUID customerId = consoleInputConverter.getId();
        consoleOutput.printRequestMessageForName();
        String name = consoleInputConverter.getCustomerName();
        consoleOutput.printRequestMessageForIsBlackCustomer();
        boolean isBlackCustomer = consoleInputConverter.getIsBlackCustomer();
        customerService.update(new UpdateCustomerRequestDto(customerId, name, isBlackCustomer).toCustomer());
        consoleOutput.printSuccessToUpdate();
    }

    private void deleteCustomer() {
        consoleOutput.printRequestMessageForCustomerId();
        customerService.deleteCustomerById(consoleInputConverter.getId());
        consoleOutput.printSuccessToDelete();
    }

    private void deleteAllCustomers() {
        customerService.deleteAllCustomers();
        consoleOutput.printSuccessToDelete();
    }

    private void getAllBlackCustomers() {
        customerService.searchAllBlackCustomers()
                .forEach(FindCustomerResponseDto::printCustomerInfo);
        consoleOutput.printSuccessToSearch();
    }
}
