package org.programmers.springboot.basic.domain.customer.controller;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.domain.customer.dto.CustomerResponseDto;
import org.programmers.springboot.basic.domain.customer.exception.CustomerNotFoundException;
import org.programmers.springboot.basic.domain.customer.service.CustomerService;
import org.programmers.springboot.basic.util.manager.ConsoleIOManager;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class CustomerController {

    private final ConsoleIOManager consoleIOManager;
    private final CustomerService customerService;

    public CustomerController(ConsoleIOManager consoleIOManager, CustomerService customerService) {
        this.consoleIOManager = consoleIOManager;
        this.customerService = customerService;
    }

    public void blacklist() {

        this.consoleIOManager.printBlackListHandler();

        try {
            List<CustomerResponseDto> responseDtos = customerService.findBlackList();
            consoleIOManager.printBlackList(responseDtos);
        } catch (CustomerNotFoundException e) {
            log.error(e.toString());
        }
    }
}
