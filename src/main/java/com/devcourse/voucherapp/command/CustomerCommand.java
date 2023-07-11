package com.devcourse.voucherapp.command;

import com.devcourse.voucherapp.controller.CustomerController;
import com.devcourse.voucherapp.entity.customer.CustomerMenu;
import com.devcourse.voucherapp.entity.customer.request.CustomerCreateRequestDto;
import com.devcourse.voucherapp.entity.customer.request.CustomerUpdateRequestDto;
import com.devcourse.voucherapp.entity.customer.response.CustomerResponseDto;
import com.devcourse.voucherapp.entity.customer.response.CustomersResponseDto;
import com.devcourse.voucherapp.view.ViewManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerCommand {

    private final ViewManager viewManager;
    private final CustomerController customerController;

    public void run() {
        String menuOption = viewManager.readMenuOption(CustomerMenu.values());
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
        String nickname = viewManager.readCustomerNickname();

        CustomerCreateRequestDto request = new CustomerCreateRequestDto(nickname);
        CustomerResponseDto response = customerController.create(request);

        viewManager.showCustomerCreationSuccessMessage(response);
    }

    private void readAllCustomers() {
        CustomersResponseDto response = customerController.findAllCustomers();
        viewManager.showAllCustomers(response);
    }

    private void updateCustomer() {
        readAllCustomers();

        String nickname = viewManager.readCustomerNicknameToUpdate();
        String typeNumber = viewManager.readCustomerTypeNumber();

        CustomerUpdateRequestDto request = new CustomerUpdateRequestDto(typeNumber, nickname);
        CustomerResponseDto response = customerController.update(request);

        viewManager.showCustomerUpdateSuccessMessage(response);
    }

    private void deleteCustomer() {
        readAllCustomers();

        String nickname = viewManager.readVoucherNicknameToDelete();
        customerController.deleteByNickname(nickname);

        viewManager.showCustomerDeleteSuccessMessage();
    }

    private void readBlackListCustomers() {
        CustomersResponseDto response = customerController.findBlackListCustomers();
        viewManager.showAllCustomers(response);
    }
}
