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
        UUID voucherId = UUID.randomUUID();
        VoucherType voucherType = consoleVoucherType();
        DiscountValue discountValue = consoleDiscountValue(voucherType);
        UUID customerId = consoleId();
        return new VoucherDto(voucherId, voucherType, discountValue, customerId);
    }

    public UUID consoleId() {
        return inputConsole.readId();
    }

    private String consoleName() {
        return inputConsole.readName();
    }

    private boolean consoleBlack() {
        return inputConsole.readBlack();
    }

    public CustomerDto consoleCustomerDto() {
        UUID id = UUID.randomUUID();
        String name = consoleName();
        boolean isBlack = consoleBlack();
        return new CustomerDto(id, name, isBlack);
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
