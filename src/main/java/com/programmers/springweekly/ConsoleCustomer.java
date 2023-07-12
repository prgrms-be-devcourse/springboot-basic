package com.programmers.springweekly;

import com.programmers.springweekly.controller.CustomerController;
import com.programmers.springweekly.domain.CustomerMenu;
import com.programmers.springweekly.dto.customer.request.CustomerUpdateRequest;
import com.programmers.springweekly.dto.customer.response.CustomerListResponse;
import com.programmers.springweekly.view.Console;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class ConsoleCustomer {

    private final CustomerController customerController;
    private final Console console;

    public void menu() {
        console.outputCustomerMenuGuide();
        CustomerMenu customerMenu = CustomerMenu.from(console.inputMessage());

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
        CustomerListResponse customerListResponse = customerController.findAll();

        if (CollectionUtils.isEmpty(customerListResponse.getCustomerList())) {
            console.outputErrorMessage("고객이 저장되어 있지 않습니다.");
            return;
        }

        console.outputGetCustomerList(customerListResponse);
    }

    private void deleteCustomer() {
        console.outputCustomerUUIDGuide();
        UUID customerId = console.inputUUID();
        customerController.existById(customerId);

        customerController.deleteById(customerId);
        console.outputCompleteGuide();
    }

    private void updateCustomer() {
        console.outputCustomerUUIDGuide();
        UUID customerId = console.inputUUID();

        console.outputCustomerUpdateGuide();
        CustomerUpdateRequest customerUpdateRequest = console.inputCustomerUpdate(customerId);

        customerController.update(customerUpdateRequest);
        console.outputCompleteGuide();
    }

    private void createCustomer() {
        console.outputCustomerCreateGuide();

        customerController.save(console.inputCustomerCreate());
        console.outputCompleteGuide();
    }

    private void getBlackList() {
        CustomerListResponse customerBlacklistResponse = customerController.getBlackList();

        if (CollectionUtils.isEmpty(customerBlacklistResponse.getCustomerList())) {
            console.outputErrorMessage("블랙 리스트인 고객이 없습니다.");
            return;
        }

        console.outputGetCustomerList(customerBlacklistResponse);
    }
}
