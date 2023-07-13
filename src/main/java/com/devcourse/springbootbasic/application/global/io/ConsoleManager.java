package com.devcourse.springbootbasic.application.global.io;

import com.devcourse.springbootbasic.application.customer.controller.CustomerDto;
import com.devcourse.springbootbasic.application.global.model.CommandMenu;
import com.devcourse.springbootbasic.application.voucher.controller.VoucherDto;
import com.devcourse.springbootbasic.application.voucher.model.DiscountValue;
import com.devcourse.springbootbasic.application.voucher.model.VoucherType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ConsoleManager {

    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;

    public ConsoleManager(InputConsole inputConsole, OutputConsole outputConsole) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
    }

    public void consoleError(Exception e) {
        outputConsole.printError(e);
    }

    public void consoleClosePlatform() {
        outputConsole.closePlatform();
    }

    public CommandMenu consoleCommandMenu() {
        outputConsole.showCommandMenu();
        return inputConsole.readCommandMenu();
    }

    private VoucherType consoleVoucherType() {
        outputConsole.showVoucherType();
        return inputConsole.readVoucherType();
    }

    private DiscountValue consoleDiscountValue(VoucherType voucherType) {
        return inputConsole.readDiscountValue(voucherType);
    }

    public VoucherDto consoleVoucherDto() {
        var voucherId = UUID.randomUUID();
        var voucherType = consoleVoucherType();
        var discountValue = consoleDiscountValue(voucherType);
        var customerId = consoleId();
        return new VoucherDto(voucherId, voucherType, discountValue, customerId);
    }

    public UUID consoleId() {
        return inputConsole.readId();
    }

    public String consoleName() {
        return inputConsole.readName();
    }

    public CustomerDto consoleCustomerDto() {
        var id = UUID.randomUUID();
        var name = consoleName();
        return new CustomerDto(id, name);
    }

    public void printVoucherDto(VoucherDto voucherDto) {
        outputConsole.printMessage(voucherDto.toString());
    }

    public void printCustomerDto(CustomerDto customerDto) {
        outputConsole.printMessage(customerDto.toString());
    }

    public void printVoucherList(List<VoucherDto> list) {
        outputConsole.printMessage(OutputMessage.LIST_VOUCHERS.getMessageText());
        outputConsole.printList(
                list.stream()
                        .map(VoucherDto::toString)
                        .toList()
        );
    }

    public void printCustomerList(List<CustomerDto> list) {
        outputConsole.printMessage(OutputMessage.LIST_CUSTOMERS.getMessageText());
        outputConsole.printList(
                list.stream()
                        .map(CustomerDto::toString)
                        .toList()
        );
    }

}
