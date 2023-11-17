package org.prgrms.kdtspringdemo.customer.controller;

import org.prgrms.kdtspringdemo.customer.service.CustomerService;
import org.prgrms.kdtspringdemo.dto.CustomerRequestDto;
import org.prgrms.kdtspringdemo.dto.CustomerViewDto;
import org.prgrms.kdtspringdemo.view.InputConsole;
import org.prgrms.kdtspringdemo.view.OutputConsole;
import org.prgrms.kdtspringdemo.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class CustomerController {
    private final CustomerService customerService;
    private final WalletService walletService;
    private final InputConsole inputConsole = new InputConsole();
    private final OutputConsole outputConsole = new OutputConsole();
    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService, WalletService walletService) {
        this.customerService = customerService;
        this.walletService = walletService;
    }

    public void insert() {
        try {
            UUID customerId = UUID.randomUUID();
            outputConsole.getCustomerName();
            String name = inputConsole.getString();
            outputConsole.getCustomerIsBlack();
            boolean isBlack = Boolean.parseBoolean(inputConsole.getString());

            customerService.insert(new CustomerRequestDto(name, isBlack));
            walletService.create(customerId); // 고객 생성 시 지갑 자동 생성
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("유효한 UUID 값이 아닙니다.");
        }
    }

    public void printAllCustomers() {
        customerService.getCustomerViewDtoLists().forEach(outputConsole::printCustomer);
    }

    public void printAllBlackListCustomer() {
        List<CustomerViewDto> customerList = customerService.getBlackListCustomers();
        customerList.forEach(outputConsole::printCustomer);
    }

    public void endCustomerMode() {
        outputConsole.printCustomerModeEnd();
    }
}
