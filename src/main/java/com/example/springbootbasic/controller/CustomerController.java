package com.example.springbootbasic.controller;

import com.example.springbootbasic.console.console.ConsoleType;
import com.example.springbootbasic.console.input.RequestBody;
import com.example.springbootbasic.console.output.ResponseBody;
import com.example.springbootbasic.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static com.example.springbootbasic.console.ResponseType.FAIL;
import static com.example.springbootbasic.console.ResponseType.SUCCESS;
import static com.example.springbootbasic.domain.customer.CustomerMessage.MENU;

@Controller
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public ResponseBody request(RequestBody requestBody) {
        ResponseBody responseBody = new ResponseBody();
        try {
            ConsoleType consoleType = requestBody.getConsoleType();
            switch (consoleType) {
                case CUSTOMER_MENU -> responseBody = responseMenu();
                case CUSTOMER_BLACK_LIST -> responseBody = responseAllBlackCustomers();
                default -> responseBody.setStatus(FAIL);
            }
        } catch (RuntimeException e) {
            logger.error("[{}] request {}", requestBody.getStatus(), requestBody.getConsoleType());
            logger.error("[{}] response {}", responseBody.getStatus(), responseBody.getConsoleType());
        }
        return responseBody;
    }

    private ResponseBody responseMenu() {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setStatus(SUCCESS);
        responseBody.setBody(MENU.getMessage());
        return responseBody;
    }

    private ResponseBody responseAllBlackCustomers() {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setStatus(SUCCESS);
        responseBody.setBody(customerService.findAllBlackCustomers());
        return responseBody;
    }
}
