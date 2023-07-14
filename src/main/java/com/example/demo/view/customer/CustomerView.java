package com.example.demo.view.customer;

import com.example.demo.dto.CustomerDto;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class CustomerView {

    private final InputView inputView;
    private final OutputView outputView;

    public CustomerView() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public String readCustomerName() {
        outputView.printCustomerNameMessage();
        return inputView.readCustomerName();
    }

    public int readCustomerAge() {
        outputView.printCustomerAgeMessage();
        return inputView.readCustomerAge();
    }

    public void printCreateMessage(CustomerDto customerDto) {
        outputView.printCreateMessage(customerDto);
    }

    public void printVoucherList(List<CustomerDto> list) {
        outputView.printCustomerList(list);
    }

    public UUID readCustomerId() {
        return null;
    }

    public void printUpdateMessage() {
        outputView.printUpdateMessage();
    }

    public void printDeleteMessage() {
        outputView.printDeleteMessage();
    }
}
