package com.devcourse.voucherapp.command;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.controller.console.CustomerController;
import com.devcourse.voucherapp.entity.customer.CustomerMenu;
import com.devcourse.voucherapp.entity.customer.CustomerType;
import com.devcourse.voucherapp.entity.customer.dto.CustomerCreateRequestDto;
import com.devcourse.voucherapp.entity.customer.dto.CustomerResponseDto;
import com.devcourse.voucherapp.entity.customer.dto.CustomerUpdateRequestDto;
import com.devcourse.voucherapp.exception.CustomerException;
import com.devcourse.voucherapp.view.CustomerView;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("local")
public class CustomerCommand {

    private final CustomerView customerView;
    private final CustomerController customerController;
    private boolean isRunning;

    public void run() {
        isRunning = true;

        while (isRunning) {
            customerView.showMenu();

            try {
                String menuOption = customerView.readUserInput();
                CustomerMenu selectedMenu = CustomerMenu.from(menuOption);
                executeMenu(selectedMenu);
            } catch (CustomerException e) {
                log.error("고객 메뉴에서 예외 발생 - {} | '{}' | 사용자 입력 : {}", e.getRule(), e.getMessage(), e.getCauseInput(), e);
                customerView.showExceptionMessage(format("{0} | 현재 입력 : {1}", e.getMessage(), e.getCauseInput()));
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
        CustomerResponseDto findResponse = customerController.findCustomerByNickname(nickname);
        String typeOption = customerView.readCustomerTypeOption();
        CustomerType newCustomerType = CustomerType.from(typeOption);

        CustomerUpdateRequestDto request = new CustomerUpdateRequestDto(findResponse.getId(), newCustomerType, nickname);
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
