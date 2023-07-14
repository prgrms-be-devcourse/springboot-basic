package com.devcourse.voucherapp.command;

import com.devcourse.voucherapp.controller.CustomerController;
import com.devcourse.voucherapp.entity.customer.CustomerMenu;
import com.devcourse.voucherapp.entity.customer.dto.CustomerCreateRequestDto;
import com.devcourse.voucherapp.entity.customer.dto.CustomerResponseDto;
import com.devcourse.voucherapp.entity.customer.dto.CustomerUpdateRequestDto;
import com.devcourse.voucherapp.view.CustomerView;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerCommand {

    private final CustomerView customerView;
    private final CustomerController customerController;
    private boolean isRunning = true;

    public void run() {
        while (isRunning) {
            customerView.showMenu();

            try {
                String menuOption = customerView.readUserInput();
                CustomerMenu selectedMenu = CustomerMenu.from(menuOption);
                executeMenu(selectedMenu);
            } catch (Exception e) {
                String message = e.getMessage();
                log.error(message);
                customerView.showExceptionMessage(message);
            }
        }
    }

    private void executeMenu(CustomerMenu selectedMenu) {
        switch (selectedMenu) {
            case CREATE -> createCustomer();
            case READ_ALL -> readAllCustomers();
            case UPDATE -> updateCustomer();
            case DELETE -> deleteCustomer();
            case READ_BLACK_LIST -> readBlackListCustomers();
            case HOME -> isRunning = false;
        }
    }

    private void createCustomer() {
        String nickname = customerView.readCustomerNickname();

        CustomerCreateRequestDto request = new CustomerCreateRequestDto(nickname);
        CustomerResponseDto response = customerController.create(request);

        customerView.showCustomerCreationSuccessMessage(response);
    }

    private void readAllCustomers() {
        List<CustomerResponseDto> response = customerController.findAllCustomers();
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
        List<CustomerResponseDto> response = customerController.findBlackListCustomers();
        customerView.showAllCustomers(response);
    }
}
