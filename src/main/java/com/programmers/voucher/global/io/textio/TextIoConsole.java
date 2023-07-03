package com.programmers.voucher.global.io.textio;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.domain.customer.dto.request.CustomerCreateRequest;
import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;
import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.io.ConsoleCommandType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TextIoConsole implements Console {
    private final TextIoInput textIoInput;
    private final TextIoOutput textIoOutput;

    public TextIoConsole(TextIoInput textIoInput, TextIoOutput textIoOutput) {
        this.textIoInput = textIoInput;
        this.textIoOutput = textIoOutput;
    }

    @Override
    public ConsoleCommandType inputInitialCommand() {
        return textIoInput.inputInitialCommand();
    }

    @Override
    public VoucherCreateRequest inputVoucherCreateInfo() {
        return textIoInput.inputVoucherCreateInfo();
    }

    @Override
    public CustomerCreateRequest inputCustomerCreateInfo() {
        return textIoInput.inputCustomerCreateInfo();
    }

    @Override
    public void printCommandSet() {
        textIoOutput.printCommandSet();
    }

    @Override
    public void printVouchers(List<Voucher> vouchers) {
        textIoOutput.printVouchers(vouchers);
    }

    @Override
    public void printCustomers(List<Customer> customers) {
        textIoOutput.printCustomers(customers);
    }

    @Override
    public void print(String result) {
        textIoOutput.print(result);
    }

    @Override
    public void exit() {
        textIoOutput.exit();
    }
}
