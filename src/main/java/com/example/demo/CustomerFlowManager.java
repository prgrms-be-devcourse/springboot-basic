package com.example.demo;

import com.example.demo.controller.CustomerController;
import com.example.demo.dto.CustomerDto;
import com.example.demo.enums.CustomerCommandType;
import com.example.demo.view.customer.CustomerView;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class CustomerFlowManager {

    private final CustomerController customerController;
    private final CustomerView customerView;

    public void runMenu() {
        try {
            CustomerCommandType customerCommandType = customerView.readCommandOption();

            switch (customerCommandType) {
                case CREATE_CUSTOMER -> {
                    String name = customerView.readCustomerName();
                    int age = customerView.readCustomerAge();
                    CustomerDto customerDto = customerController.create(name, age);
                    customerView.printCreateMessage(customerDto);
                }
                case PRINT_CUSTOMER_LIST -> {
                    List<CustomerDto> customerDtoList = customerController.readList();
                    customerView.printVoucherList(customerDtoList);
                }
                case UPDATE_CUSTOMER_NAME -> {
                    UUID id = customerView.readCustomerId();
                    String name = customerView.readCustomerName();
                    customerController.updateName(id, name);
                    customerView.printUpdateMessage();
                }
                case DELETE_CUSTOMER -> {
                    UUID id = customerView.readCustomerId();
                    customerController.deleteById(id);
                    customerView.printDeleteMessage();
                }
                default -> throw new IllegalArgumentException(String.format("입력하신 %s은 올바르지 않은 커맨드입니다.", customerCommandType.name()));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

}
