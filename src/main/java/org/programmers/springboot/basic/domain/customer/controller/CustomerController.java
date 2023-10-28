package org.programmers.springboot.basic.domain.customer.controller;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.domain.customer.dto.CustomerRequestDto;
import org.programmers.springboot.basic.domain.customer.dto.CustomerResponseDto;
import org.programmers.springboot.basic.domain.customer.exception.*;
import org.programmers.springboot.basic.domain.customer.mapper.CustomerEntityMapper;
import org.programmers.springboot.basic.domain.customer.service.CustomerService;
import org.programmers.springboot.basic.util.manager.ConsoleIOManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class CustomerController {

    private final ConsoleIOManager consoleIOManager;
    private final CustomerService customerService;

    @Autowired
    public CustomerController(ConsoleIOManager consoleIOManager, CustomerService customerService) {
        this.consoleIOManager = consoleIOManager;
        this.customerService = customerService;
    }

    public void create() {

        String name = null;
        String email = null;

        consoleIOManager.printCustomerCreateHandler();

        try {
            name = consoleIOManager.getInput();
            email = consoleIOManager.getInput();
            CustomerRequestDto requestDto = CustomerEntityMapper.INSTANCE.mapToRequestDto(name, email);
            this.customerService.create(requestDto);
        } catch (IllegalEmailException e) {
            log.warn("Your input is Invalid Email Type: '{}'", email);
        } catch (DuplicateEmailException e) {
            log.warn("Duplicate email '{}' is already exists", email);
        }
    }

    public void list() {

        List<CustomerResponseDto> responseDtos = this.customerService.findAll();
        consoleIOManager.printCustomer(responseDtos);
    }

    public void blacklist() {

        List<CustomerResponseDto> responseDtos = this.customerService.findByCustomerIsBlack();
        consoleIOManager.printBlackList(responseDtos);
    }

    public void addToBlackList() {

        String input = null;

        consoleIOManager.printAddBlackHandler();

        try {
            input = consoleIOManager.getInput();
            CustomerRequestDto requestDto = CustomerEntityMapper.INSTANCE.mapToRequestDtoWithUUID(input);
            this.customerService.addBlackCustomer(requestDto);
        } catch (IllegalArgumentException e) {
            log.warn("Your input is Invalid UUID string: {}", input);
        } catch (DuplicateBlackCustomerException e) {
            log.warn("customer of customerId '{}' is already exists from blacklist", input);
        } catch (CustomerNotFoundException e) {
            log.warn("No customer of customerId '{}' found", input);
        }
    }

    public void deleteFromBlackList() {

        String input = null;

        consoleIOManager.printDeleteBlackHandler();

        try {
            input = consoleIOManager.getInput();
            CustomerRequestDto requestDto = CustomerEntityMapper.INSTANCE.mapToRequestDtoWithUUID(input);
            this.customerService.deleteBlackCustomer(requestDto);
        } catch (IllegalArgumentException e) {
            log.error("Your input is Invalid UUID string: '{}'", input);
        } catch (BlackCustomerNotFoundException e) {
            log.warn("No customer found from blacklist that matches the customerId '{}'", input);
        } catch (CustomerNotFoundException e) {
            log.warn("No customer of customerId '{}' found", input);
        }
    }

    public void delete() {

        String input = null;

        consoleIOManager.printCustomerDeleteHandler();

        try {
            input = consoleIOManager.getInput();
            CustomerRequestDto requestDto = CustomerEntityMapper.INSTANCE.mapToRequestDtoWithUUID(input);
            this.customerService.deleteCustomer(requestDto);
        } catch (IllegalArgumentException e) {
            log.error("Your input is Invalid UUID string: '{}'", input);
        } catch (CustomerNotFoundException e) {
            log.warn("No customer of customerId '{}' found", input);
        }
    }

    public void deleteAll() {

        consoleIOManager.printDeleteAllHandler();

        String input = consoleIOManager.getInput();
        switch (input) {
            case "Y", "y" -> this.customerService.deleteAll();
            default -> {}
        }
    }
}
