package com.prgrms.spring.controller;

import com.prgrms.spring.controller.dto.request.CustomerCreateRequestDto;
import com.prgrms.spring.domain.customer.Customer;
import com.prgrms.spring.exception.Error;
import com.prgrms.spring.exception.Success;
import com.prgrms.spring.io.ConsoleView;
import com.prgrms.spring.service.customer.CustomerService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final ConsoleView consoleView;
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public void createCustomer() {
        consoleView.showGetName();
        String name = consoleView.getCustomerName();
        consoleView.showGetEmail();
        String email = consoleView.getCustomerEmail();
        var request = CustomerCreateRequestDto.of(name, email);
        Customer customer = customerService.createCustomer(request);
        if (customer == null) {
            consoleView.showErrorMsg(Error.CREATE_CUSTOMER_EXCEPTION);
            logger.error("고객 등록 실패");
            return;
        }
        consoleView.showSuccessMsg(Success.CREATE_CUSTOMER_SUCCESS);
    }

    public void getAllCustomers() {
        consoleView.showAllCustomers(customerService.getAllCustomers());
    }
}
