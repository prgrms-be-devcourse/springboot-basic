package com.example.demo.view.voucher;

import com.example.demo.dto.VoucherDto;
import com.example.demo.util.CommandType;
import com.example.demo.util.VoucherType;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VoucherView {

    private final InputView inputView;
    private final OutputView outputView;

    public VoucherView() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public CommandType readCommandOption() {
        outputView.printCommandOptionMessage();
        return inputView.readCommandOption();
    }

    public VoucherType readVoucherOption() {
        outputView.printVoucherOptionMessage();
        return inputView.readVoucherOption();
    }

    public int readVoucherAmount(VoucherType voucherType) {
        outputView.printVoucherAmountInfoMessage(voucherType);
        return inputView.readVoucherAmount(voucherType);
    }

    public void printCreateMessage(VoucherDto voucherDto) {
        outputView.printCreateMessage(voucherDto);
    }

    public void printVoucherList(List<VoucherDto> list) {
        outputView.printVoucherList(list);
    }
}
