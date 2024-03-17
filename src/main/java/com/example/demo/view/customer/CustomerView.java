package com.example.demo.view.customer;

import com.example.demo.dto.CustomerDto;
import com.example.demo.enums.CustomerCommandType;
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

    public CustomerCommandType readCommandOption() {
        outputView.printCommandOptionMessage();
        return inputView.readCommandOption();
    }

    public UUID readCustomerId() {
        outputView.printCustomerIdMessage();
        return inputView.readCustomerId();
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

    public void printUpdateMessage() {
        outputView.printUpdateMessage();
    }

    public void printDeleteMessage() {
        outputView.printDeleteMessage();
    }
}
