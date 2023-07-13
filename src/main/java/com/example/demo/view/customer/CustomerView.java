package com.example.demo.view.customer;

import com.example.demo.dto.CustomerDto;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class CustomerView {

    public String readCustomerName() {
        return null;
    }

    public int readCustomerAge() {
        return 0;
    }

    public void printCreateMessage(CustomerDto customerDto) {
    }

    public void printVoucherList(List<CustomerDto> customerDtoList) {
    }

    public UUID readCustomerId() {
        return null;
    }

    public void printUpdateMessage() {
    }

    public void printDeleteMessage() {
    }
}
