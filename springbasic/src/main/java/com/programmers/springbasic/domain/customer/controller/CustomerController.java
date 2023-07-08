package com.programmers.springbasic.domain.customer.controller;

import com.programmers.springbasic.domain.customer.dto.request.CustomerCreateRequestDTO;
import com.programmers.springbasic.domain.customer.dto.request.CustomerDeleteRequestDTO;
import com.programmers.springbasic.domain.customer.dto.request.CustomerSingleFindRequestDTO;
import com.programmers.springbasic.domain.customer.dto.request.CustomerUpdateRequestDTO;
import com.programmers.springbasic.domain.customer.service.CustomerService;
import com.programmers.springbasic.domain.customer.validator.CustomerCommandValidator;
import com.programmers.springbasic.domain.customer.validator.CustomerCreateRequestValidator;
import com.programmers.springbasic.domain.customer.validator.CustomerIdValidator;
import com.programmers.springbasic.domain.customer.view.CustomerCommandMessage;
import com.programmers.springbasic.domain.customer.view.CustomerCommandOption;
import com.programmers.springbasic.domain.customer.view.CustomerInfoResponse;
import com.programmers.springbasic.domain.io.IOConsole;
import com.programmers.springbasic.domain.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;


@Profile("dev")
@Controller
@RequiredArgsConstructor
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;
    private final VoucherService voucherService;
    private final IOConsole ioConsole;

    public void run() {
        ioConsole.showCustomerMenu();

        String inputCommand = ioConsole.getInput();
        CustomerCommandValidator.validateCustomerCommand(inputCommand);

        handleCommand(inputCommand);
    }

    private void handleCommand(String validCommand) {
        switch (CustomerCommandOption.of(validCommand)) {
            case CREATE: {
                executeCreateCommand();
                break;
            }
            case LIST: {
                executeListCommand();
                break;
            }
            case READ: {
                executeReadCommand();
                break;
            }
            case UPDATE: {
                executeUpdateCommand();
                break;
            }
            case DELETE: {
                executeDeleteCommand();
                break;
            }
            case SHOW: {
                // executeShowCommand();    // TODO: 고객에게 할당된 voucher 조회
                break;
            }
            case EXIT: {
                executeExitCommand();
            }
        }
    }

    private void executeCreateCommand() {
        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_CREATE_MESSAGE.getMessage());

        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_CREATE_NAME_MESSAGE.getMessage());
        String name = ioConsole.getInput();

        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_CREATE_EMAIL_MESSAGE.getMessage());
        String email = ioConsole.getInput();

        CustomerCreateRequestDTO customerCreateRequestDTO = new CustomerCreateRequestDTO(name, email);
        CustomerCreateRequestValidator.validateCreateCustomerRequest(customerCreateRequestDTO);

        customerService.createCustomer(customerCreateRequestDTO);
        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_CREATE_FINISHED_MESSAGE.getMessage());
    }

    private void executeListCommand() {
        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_LIST_MESSAGE.getMessage());

        List<String> customerInfoViews = customerService.getAllInfo()
                .stream()
                .map(CustomerInfoResponse::new)
                .map(CustomerInfoResponse::getCustomerInfoView)
                .collect(Collectors.toList());

        ioConsole.printListOutput(customerInfoViews);
    }

    private void executeReadCommand() {
        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_RAED_ID_MESSAGE.getMessage());

        String customerId = ioConsole.getInput();
        CustomerIdValidator.validateCustomerId(customerId);

        CustomerSingleFindRequestDTO customerSingleFindRequestDTO = new CustomerSingleFindRequestDTO(customerId);

        CustomerInfoResponse customerInfoResponse = new CustomerInfoResponse(customerService.findCustomer(customerSingleFindRequestDTO));
        ioConsole.printSingleOutput(customerInfoResponse.getCustomerInfoView());
    }

    private void executeUpdateCommand() {
        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_UPDATE_ID_MESSAGE.getMessage());
        String customerId = ioConsole.getInput();

        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_UPDATE_NAME_MESSAGE.getMessage());
        String newName = ioConsole.getInput();

        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_UPDATE_EMAIL_MESSAGE.getMessage());
        String newEmail = ioConsole.getInput();

        CustomerIdValidator.validateCustomerId(customerId);
        CustomerUpdateRequestDTO customerUpdateRequestDTO = new CustomerUpdateRequestDTO(customerId, newName, newEmail);

        customerService.updateCustomer(customerUpdateRequestDTO);
        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_UPDATE_ID_MESSAGE.getMessage());
    }

    private void executeDeleteCommand() {
        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_DELETE_ID_MESSAGE.getMessage());

        String customerId = ioConsole.getInput();
        CustomerIdValidator.validateCustomerId(customerId);

        CustomerDeleteRequestDTO customerDeleteRequestDTO = new CustomerDeleteRequestDTO(customerId);
        customerService.removeCustomer(customerDeleteRequestDTO);
        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_DELETE_FINISHED_MESSAGE.getMessage());
    }

//    // TODO: 고객에게 할당된 Voucher 조회
//    private void executeShowCommand() {
//        ioConsole.printSingleOutput("voucher를 조회할 고객의 id을 입력하세요");
//
//        String customerId = ioConsole.getInput();
//        CustomerIdValidator.validateCustomerId(customerId);
//
//        ioConsole.printListOutput(voucherService.getAllVoucherInfoByCustomerId(customerIdValidator));
//    }

    private void executeExitCommand() {
        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_EXIT_MESSAGE.getMessage());
    }
}
