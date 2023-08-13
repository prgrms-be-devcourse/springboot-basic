package org.promgrammers.voucher.controller;


import lombok.RequiredArgsConstructor;
import org.promgrammers.voucher.domain.CustomerType;
import org.promgrammers.voucher.domain.dto.customer.CustomerListResponseDto;
import org.promgrammers.voucher.domain.dto.customer.CustomerRequestDto;
import org.promgrammers.voucher.domain.dto.customer.CustomerResponseDto;
import org.promgrammers.voucher.service.CustomerService;
import org.promgrammers.voucher.view.Console;
import org.promgrammers.voucher.view.CustomerAction;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final Console console;
    private final CustomerService customerService;

    private boolean isRunning = true;

    public synchronized void userPostController() {
        while (isRunning) {
            try {
                String inputCommand = console.askCustomerAction();
                CustomerAction customerAction = CustomerAction.fromAction(inputCommand);

                execute(customerAction);
            } catch (RuntimeException e) {
                console.print(e.getMessage());
            }
        }
    }

    public void execute(CustomerAction customerAction) {

        switch (customerAction) {
            case CREATE: {
                CustomerResponseDto customer = create();
                console.print("고객 등록이 완료되었습니다. : \n   " + customer.toString());
                break;
            }
            case FIND_ALL: {
                CustomerListResponseDto customerList = findAll();
                console.print(customerList.toString());
                break;
            }
            case FIND_ONE: {
                findByType();
                break;
            }
            case UPDATE: {
                CustomerResponseDto updatedCustomer = update();
                console.print(updatedCustomer.toString());
                break;
            }
            case DELETE: {
                deleteType();
                console.print("삭제 되었습니다.");
                break;
            }
            case ASSIGN: {
                assignType();
                console.print("바우처가 고객에게 할당 되었습니다");
                break;
            }
            case EXIT:
                exitProgram();
                break;
        }
    }

    private void deleteType() {
        String inputType = console.askRemoveCustomerFindType();

        switch (inputType) {
            case "id":
                deleteCustomerById();
                break;
            case "all":
                deleteAll();
                break;
            default:
                throw new IllegalArgumentException("Invalid delete type: " + inputType);
        }
    }

    private CustomerResponseDto create() {
        console.print("고객 이름을 입력해 주세요.");
        String inputUsername = console.input();

        CustomerRequestDto customerRequest = new CustomerRequestDto(UUID.randomUUID(), inputUsername);
        return customerService.createCustomer(customerRequest);
    }

    private void assignType(){
        console.print("고객 아이디를 입력해 주세요.");
        String inputUserId = console.input();
        UUID customerId = UUID.fromString(inputUserId);

        console.print("바우처 아이디를 입력해 주세요.");
        String inputVoucherId = console.input();
        UUID voucherId = UUID.fromString(inputUserId);

        customerService.assignVoucherToCustomer(customerId, voucherId);
    }

    private CustomerResponseDto update() {
        console.print("고객 아이디를 입력해 주세요.");
        String inputUserId = console.input();
        UUID customerId = UUID.fromString(inputUserId);

        console.print("수정 할 이름을 적어주세요.");
        String requestUsername = console.input();

        CustomerRequestDto updateCustomerRequest = new CustomerRequestDto(customerId, requestUsername);
        return customerService.updateCustomer(updateCustomerRequest);
    }

    private void findByType() {
        String inputType = console.askForCustomerFindType();

        switch (inputType) {
            case "id": {
                findCustomerById();
                break;
            }
            case "name": {
                findCustomerByUsername();
                break;
            }
            case "voucherId": {
                findByVoucherId();
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid search type: " + inputType);
            }
        }
    }


    private CustomerResponseDto findCustomerById() {
        console.print("고객 아이디를 입력해 주세요.");
        String inputUserId = console.input();
        UUID voucherId = UUID.fromString(inputUserId);

        CustomerResponseDto customer = customerService.findCustomerById(voucherId);
        console.print(customer.toString());

        return customer;
    }

    private CustomerResponseDto findCustomerByUsername() {
        console.print("조회할 고객 이름을 입력해 주세요");
        String inputUsername = console.input();

        CustomerResponseDto customer = customerService.findCustomerByUsername(inputUsername);
        console.print(customer.toString());

        return customer;
    }


    private CustomerResponseDto findByVoucherId() {
        console.print("바우처 아이디를 입력해 주세요.");
        String inputVoucherId = console.input();
        UUID voucherId = UUID.fromString(inputVoucherId);

        CustomerResponseDto customerResponse = customerService.findByVoucherId(voucherId);
        console.print(customerResponse.toString());
        return customerResponse;
    }

    private CustomerListResponseDto findAll() {
        return customerService.findAllCustomers();
    }

    public void deleteAll() {
        customerService.deleteAllCustomers();
        console.print("모든 고객이 삭제 되었습니다.");
    }

    private void deleteCustomerById() {
        console.print("바우처 아이디를 입력해 주세요.");
        String inputVoucherId = console.input();
        UUID voucherId = UUID.fromString(inputVoucherId);

        customerService.deleteById(voucherId);
    }

    private void exitProgram() {
        isRunning = false;
    }

}