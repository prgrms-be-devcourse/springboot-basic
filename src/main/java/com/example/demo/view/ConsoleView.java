package com.example.demo.view;

import com.example.demo.dto.VoucherDto;
import com.example.demo.util.CommandType;
import com.example.demo.util.VoucherType;
import java.util.List;

public class ConsoleView {

    private final InputView inputView;
    private final OutputView outputView;

    public ConsoleView() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void printStartingMessage() {
        outputView.printStartingMessage();
    }

    public CommandType readCommandOption() {
        outputView.printCommandOptionMessage();
        return inputView.readCommandOption();
    }

    public VoucherType readVoucherOption() {
        outputView.printVoucherOptionMessage();
        return inputView.readVoucherOption();
    }

    public Integer readVoucherAmount(VoucherType voucherType) {
        outputView.printVoucherAmountInfoMessage(voucherType);
        return inputView.readVoucherAmount(voucherType);
    }

    public void printVoucherList(List<VoucherDto> list) {
        outputView.printVoucherList(list);
    }
}
