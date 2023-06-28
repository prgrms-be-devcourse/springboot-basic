package com.programmers.voucher.global.io;

import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;
import org.springframework.stereotype.Component;

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
    public void printCommandSet() {
        textIoOutput.printCommandSet();
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
