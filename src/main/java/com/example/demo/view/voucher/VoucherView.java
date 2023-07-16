package com.example.demo.view.voucher;

import com.example.demo.dto.VoucherDto;
import com.example.demo.util.VoucherCommandType;
import com.example.demo.util.VoucherDiscountType;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class VoucherView {

    private final InputView inputView;
    private final OutputView outputView;

    public VoucherView() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public VoucherCommandType readVoucherCommandOption() {
        outputView.printVoucherCommandOptionMessage();
        return inputView.readVoucherCommandOption();
    }

    public UUID readVoucherId() {
        outputView.printVoucherIdMessage();
        return inputView.readVoucherId();
    }

    public VoucherDiscountType readVoucherDiscountOption() {
        outputView.printVoucherOptionMessage();
        return inputView.readVoucherDiscountOption();
    }

    public int readVoucherAmount(VoucherDiscountType voucherDiscountType) {
        outputView.printVoucherAmountInfoMessage(voucherDiscountType);
        return inputView.readVoucherAmount(voucherDiscountType);
    }

    public int readVoucherAmountWithoutValidation() {
        outputView.printVoucherAmountInfoMessageWithOutTypeInfo();
        return inputView.readVoucherAmountWithoutValidation();
    }

    public void printCreateMessage(VoucherDto voucherDto) {
        outputView.printCreateMessage(voucherDto);
    }

    public void printVoucherList(List<VoucherDto> list) {
        outputView.printVoucherList(list);
    }

    public void printUpdateMessage() {
        outputView.printUpdateMessage();
    }

    public void printDeleteMessage() {
        outputView.printDeleteMessage();
    }
}
