package com.prgrms.springbootbasic;

import com.prgrms.springbootbasic.controller.customer.CustomerController;
import com.prgrms.springbootbasic.dto.customer.request.CustomerCreateRequest;
import com.prgrms.springbootbasic.dto.customer.request.CustomerUpdateRequest;
import com.prgrms.springbootbasic.dto.customer.response.CustomerListResponse;
import com.prgrms.springbootbasic.dto.customer.response.CustomerResponse;
import com.prgrms.springbootbasic.enums.customer.CustomerDeleteMenu;
import com.prgrms.springbootbasic.enums.customer.CustomerMenu;
import com.prgrms.springbootbasic.enums.customer.CustomerSelectMenu;
import com.prgrms.springbootbasic.view.Console;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsoleCustomer {

    private final CustomerController customerController;
    private final Console console;

    public void menu() {
        console.printCustomerMenu();
        switch (CustomerMenu.of(console.inputCommand())) {
            case CREATE -> createCustomer();
            case UPDATE -> updateCustomer();
            case SELECT -> selectCustomer();
            case DELETE -> deleteCustomer();
        }
    }

    //생성(Create)

    private void createCustomer() {
        console.printCustomerCreateMenu();

        CustomerCreateRequest createRequest = console.inputCustomerCreateMessage();

        customerController.create(createRequest);
        console.printCompleteMessage();
    }

    //변경
    private void updateCustomer() {
        console.printCustomerUpdateByID();
        UUID customerId = console.inputUUID();
        CustomerUpdateRequest updateRequest = console.inputCustomerUpdateMessage(customerId);

        customerController.update(updateRequest);
        console.printCompleteMessage();
    }


    private void selectCustomer() {
        console.printCustomerSelectMenu();
        //선택 - Id, CreateAt, All
        switch (CustomerSelectMenu.of(console.inputCommand())) {
            case ID -> {
                console.printCustomerSelectById();
                UUID customerId = console.inputUUID();

                if (!customerController.checkCustomerId(customerId)) {
                    console.printErrorMessage("해당 ID를 가진 고객을 찾을 수 없습니다.");
                } else {
                    CustomerResponse customerResponse = customerController.findById(customerId);
                    System.out.println("해당 고객의 ID: " + customerResponse.getCustomerId());
                    System.out.println("해당 고객의 이름: " + customerResponse.getCustomerName());
                    System.out.println("해당 고객의 이메일: " + customerResponse.getCustomerEmail());
                    System.out.println("해당 고객의 생성일: " + customerResponse.getCreateAt());
                }
            }
            //생성일 순으로 조회 - 오류
            case CREATEDAT -> {
                console.printCutomerSelectByCreatedAt();
                CustomerListResponse customerListResponse = customerController.findByCreateAt();
                if (!customerListResponse.getCustomerResponseList().isEmpty()) {
                    console.printCustomerSelectByCreatedAt(customerListResponse);
                } else {
                    console.printErrorMessage("현재 저장된 고객이 존재하지 않습니다.");
                }
            }
            //모든 리스트 조회
            case ALL -> {
                CustomerListResponse customerListResponse = customerController.findAllList();
                if (!customerListResponse.getCustomerResponseList().isEmpty()) {
                    console.printCustomerSelectAll(customerListResponse);
                } else {
                    console.printErrorMessage("현재 저장된 고객이 없습니다.");
                }
            }
            default -> console.printErrorMessage("잘못된 Customer SelectMenu를 선택하셨습니다. 다시 확인해주세요.");
        }
    }


    private void deleteCustomer() {
        console.printCustomerDeleteMenu();
        //선택 - id, all
        switch (CustomerDeleteMenu.of(console.inputCommand())) {
            case ID -> {
                console.printCustomerDeleteByID();
                UUID customerId = console.inputUUID();

                if (!customerController.checkCustomerId(customerId)) {
                    console.printErrorMessage(customerId + "찾을 고객이 존재하지 않습니다.");
                    return;
                }
                if (customerController.deleteById(customerId) == 0) {
                    console.printErrorMessage(customerId + " 삭제하려는 고객이 저장되어있지 않아 삭제할 수 없습니다.");
                    return;
                }
                console.printCompleteMessage();
            }
            case ALL -> {
                console.printCustomerDeleteByAll();
                customerController.deleteAll();
            }
            default -> {
                IllegalStateException e = new IllegalStateException("프로그램 삭제 명령어 오류");
                log.error("프로그램 삭제 명령어 오류", e);
                throw e;
            }
        }
    }
}