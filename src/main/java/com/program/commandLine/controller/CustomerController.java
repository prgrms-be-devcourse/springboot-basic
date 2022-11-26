package com.program.commandLine.controller;

import com.program.commandLine.io.MenuType;
import com.program.commandLine.model.CustomerInputData;
import com.program.commandLine.model.customer.Customer;
import com.program.commandLine.io.Console;
import com.program.commandLine.service.CustomerService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component(value = "customerController")
public class CustomerController {

    private final String REGULAR_TYPE  = "regular";
    private final CustomerService customerService;
    private final Console console;

    private enum CustomerMenuType {
        CREATE, SEARCH, BLACKLIST, DELETE
    }


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

        customerService.createCustomer(new CustomerInputData(REGULAR_TYPE,name,email));
        console.successMessageView("고객이 정상적으로 생성되었습니다.");
    }

    public void searchCustomerByName() {
        String name = console.input("조회할 고객 이름을 입력하세요 : ");
        Customer customer = customerService.getCustomerByName(name);
        console.customerView(customer);
    }

    public void deleteAllCustomer() {
        boolean recheck = console.recheckInput("정말 삭제 하시겠습니까?(Y/N) :");
        if (recheck) {
            customerService.deleteAll();
            console.successMessageView("정상적으로 삭제되었습니다.");
        }
        else console.successMessageView("삭제를 취소합니다.");
    }


    public void run() {
        console.menuView(MenuType.CUSTOMER);
        String choseMenu = console.input();
        CustomerMenuType customerMenuType = CustomerMenuType.valueOf(choseMenu.toUpperCase());
        switch (customerMenuType) {
            case CREATE -> createCustomer();
            case SEARCH -> searchCustomerByName();
            case BLACKLIST -> LookupCustomerBlackList();
            case DELETE -> deleteAllCustomer();
        }

    }
}
