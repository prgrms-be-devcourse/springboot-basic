package com.programmers.vouchermanagement.customer.presentation;

import com.programmers.vouchermanagement.exception.FileIOException;
import com.programmers.vouchermanagement.customer.application.CustomerService;
import com.programmers.vouchermanagement.customer.dto.CustomerResponseDto;
import com.programmers.vouchermanagement.customer.exception.CustomerNotFoundException;
import com.programmers.vouchermanagement.utils.ConsoleOutputManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final ConsoleOutputManager consoleOutputManager;
    private final CustomerService customerService;

    public CustomerController(ConsoleOutputManager consoleOutputManager, CustomerService customerService) {
        this.consoleOutputManager = consoleOutputManager;
        this.customerService = customerService;
    }

    public void readAllBlackList() {

        consoleOutputManager.printBlackList();

        try {
            List<CustomerResponseDto> customerResponseDtos = customerService.readAllBlackList();
            consoleOutputManager.printCustomerInfo(customerResponseDtos);

        } catch (CustomerNotFoundException | FileIOException e) {
            logger.error(e.getMessage(), e);

            consoleOutputManager.printReturnMain(e.getMessage());
        }
    }
}
