package org.programmers.springboot.basic.domain.customer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.domain.customer.dto.CustomerRequestDto;
import org.programmers.springboot.basic.domain.customer.dto.CustomerResponseDto;
import org.programmers.springboot.basic.domain.customer.exception.*;
import org.programmers.springboot.basic.domain.customer.mapper.CustomerMapper;
import org.programmers.springboot.basic.domain.customer.service.CustomerService;
import org.programmers.springboot.basic.util.manager.ConsoleIOManager;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final ConsoleIOManager consoleIOManager;
    private final CustomerService customerService;
    public void create() {

        String name = null;
        String email = null;

        consoleIOManager.printCustomerCreateHandler();

        try {
            name = consoleIOManager.getInput();
            email = consoleIOManager.getInput();
            CustomerRequestDto requestDto = CustomerMapper.INSTANCE.mapToRequestDto(name, email);
            customerService.create(requestDto);
        } catch (IllegalEmailException e) {
            log.warn("Your input is Invalid Email Type: '{}'", email);
        } catch (DuplicateEmailException e) {
            log.warn("Duplicate email '{}' is already exists", email);
        }
    }

    public void list() {

        List<CustomerResponseDto> responseDtos = customerService.findAllCustomer();
        responseDtos.forEach(CustomerResponseDto::printCustomerInfo);
    }

    public void blacklist() {

        List<CustomerResponseDto> responseDtos = customerService.findByCustomerIsBlack();
        responseDtos.forEach(CustomerResponseDto::printCustomerInfo);
    }

    public void addToBlackList() {

        String input = null;

        consoleIOManager.printAddBlackHandler();

        try {
            input = consoleIOManager.getInput();
            CustomerRequestDto requestDto = CustomerMapper.INSTANCE.mapToRequestDtoWithUUID(input);
            customerService.addBlackCustomer(requestDto);
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
            CustomerRequestDto requestDto = CustomerMapper.INSTANCE.mapToRequestDtoWithUUID(input);
            customerService.deleteBlackCustomer(requestDto);
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
            CustomerRequestDto requestDto = CustomerMapper.INSTANCE.mapToRequestDtoWithUUID(input);
            customerService.deleteCustomer(requestDto);
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
            case "Y", "y" -> customerService.deleteAll();
            default -> {}
        }
    }
}
