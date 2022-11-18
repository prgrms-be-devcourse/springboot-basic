package com.program.commandLine.controller;

import com.program.commandLine.customer.Customer;
import com.program.commandLine.io.Console;
import com.program.commandLine.service.CustomerService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component(value = "customerController")
public class CustomerController {

    private final String regularType = "regular";
    private final CustomerService customerService;
    private final Console console;

    public CustomerController(CustomerService customerService, Console console) {
        this.customerService = customerService;
        this.console = console;
    }

    public void LookupCustomerBlackList() {
        List<Customer> blackList = customerService.getBlackListCustomers();
        console.customerBlackListView(blackList);
    }

    public void createCustomer() {
        String name = console.input("고객 이름을 입력하세요 : ");
        String email = console.input("고객 email을 입력하세요 : ");

        customerService.createCustomer(regularType, UUID.randomUUID(), name, email);
        console.successMessageView("고객이 정상적으로 생성되었습니다.");
    }

    public void searchCustomerByName() {
        String name = console.input("조회할 고객 이름을 입력하세요 : ");
        Customer customer = customerService.getCustomerByName(name);
        console.customerView(customer);
    }

    public void deleteAllCustomer() {
        String recheck = console.input("정말 삭제 하시겠습니까?(Y/N) :");
        switch (recheck.toUpperCase()) {
            case "Y" -> customerService.deleteAll();
            case "N" -> {
                return;
            }
            default -> console.errorMessageView("잘못된 입력입니다.");
        }
    }
}
