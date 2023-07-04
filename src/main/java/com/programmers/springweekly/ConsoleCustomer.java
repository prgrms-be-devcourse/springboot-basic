package com.programmers.springweekly;

import com.programmers.springweekly.controller.CustomerController;
import com.programmers.springweekly.domain.CustomerMenu;
import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.dto.CustomerUpdateDto;
import com.programmers.springweekly.view.Console;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConsoleCustomer {

    private final CustomerController customerController;
    private final Console console;

    public void run() {
        console.outputCustomerMenuGuide();
        CustomerMenu customerMenu = CustomerMenu.findCustomerMenu(console.inputMessage());

        switch (customerMenu) {
            case CREATE -> createCustomer();
            case UPDATE -> updateCustomer();
            case DELETE -> deleteCustomer();
            case SELECT -> selectCustomer();
            case BLACKLIST -> getBlackList();
            default -> throw new IllegalArgumentException("Input :" + customerMenu + "찾으시는 고객 메뉴가 없습니다.");
        }
    }

    private void selectCustomer() {
        List<Customer> customerList = customerController.findAll();

        if (customerList.isEmpty()) {
            console.outputErrorMessage("고객이 저장되어 있지 않습니다.");
            return;
        }

        console.outputGetCustomerList(customerList);
    }

    private void deleteCustomer() {
        console.outputUUIDGuide();
        UUID customerId = console.inputUUID();
        customerController.deleteById(customerId);
        console.outputCompleteGuide();
    }

    private void updateCustomer() {
        console.outputUUIDGuide();
        UUID customerId = console.inputUUID();
        console.outputCustomerUpdateGuide();
        CustomerUpdateDto customerUpdateDto = console.inputCustomerUpdate(customerId);
        customerController.update(customerUpdateDto);
        console.outputCompleteGuide();
    }

    private void createCustomer() {
        console.outputCustomerCreateGuide();
        customerController.save(console.inputCustomerCreate());
        console.outputCompleteGuide();
    }

    private void getBlackList() {
        List<Customer> customerBlacklist = customerController.getBlackList();

        if (customerBlacklist.isEmpty()) {
            console.outputErrorMessage("블랙 리스트인 고객이 없습니다.");
            return;
        }

        console.outputGetCustomerList(customerBlacklist);
    }
}
