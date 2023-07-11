package com.devcourse.voucherapp.command;

import com.devcourse.voucherapp.controller.CustomerController;
import com.devcourse.voucherapp.entity.customer.CustomerMenu;
import com.devcourse.voucherapp.entity.customer.request.CustomerCreateRequestDto;
import com.devcourse.voucherapp.entity.customer.request.CustomerUpdateRequestDto;
import com.devcourse.voucherapp.entity.customer.response.CustomerResponseDto;
import com.devcourse.voucherapp.entity.customer.response.CustomersResponseDto;
import com.devcourse.voucherapp.view.CommonView;
import com.devcourse.voucherapp.view.CustomerView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerCommand {

    private final CommonView commonView;
    private final CustomerView customerView;
    private final CustomerController customerController;

    public void run() {
        customerView.showTitle();

        String menuOption = commonView.readMenuOption(CustomerMenu.values());
        CustomerMenu selectedMenu = CustomerMenu.from(menuOption);

        executeMenu(selectedMenu);
    }

    private void executeMenu(CustomerMenu selectedMenu) {
        switch (selectedMenu) {
            case CREATE -> createCustomer();
            case READ_ALL -> readAllCustomers();
            case UPDATE -> updateCustomer();
            case DELETE -> deleteCustomer();
            case READ_BLACK_LIST -> readBlackListCustomers();
        }
    }

    private void createCustomer() {
        String nickname = customerView.readCustomerNickname();

        CustomerCreateRequestDto request = new CustomerCreateRequestDto(nickname);
        CustomerResponseDto response = customerController.create(request);

        customerView.showCustomerCreationSuccessMessage(response);
    }

    private void readAllCustomers() {
        CustomersResponseDto response = customerController.findAllCustomers();
        customerView.showAllCustomers(response);
    }

    private void updateCustomer() {
        readAllCustomers();

        String nickname = customerView.readCustomerNicknameToUpdate();
        String typeNumber = customerView.readCustomerTypeNumber();

        CustomerUpdateRequestDto request = new CustomerUpdateRequestDto(typeNumber, nickname);
        CustomerResponseDto response = customerController.update(request);

        customerView.showCustomerUpdateSuccessMessage(response);
    }

    private void deleteCustomer() {
        readAllCustomers();

        String nickname = customerView.readCustomerNicknameToDelete();
        customerController.deleteByNickname(nickname);

        customerView.showCustomerDeleteSuccessMessage();
    }

    private void readBlackListCustomers() {
        CustomersResponseDto response = customerController.findBlackListCustomers();
        customerView.showAllCustomers(response);
    }
}
