package com.waterfogsw.voucher.console.handler;

import com.waterfogsw.voucher.console.Command;
import com.waterfogsw.voucher.console.Input;
import com.waterfogsw.voucher.console.Output;
import com.waterfogsw.voucher.global.FrontController;
import com.waterfogsw.voucher.global.PostRequest;

public class CreateHandler extends CommandHandler {

    public CreateHandler(Input input, Output output, FrontController frontController) {
        super(input, output, frontController);
    }

    @Override
    public void handle() {
        output.printVoucherTypes();

        String voucherType = input.inputType();
        String value = input.inputValue();

        final var requestMessage = new PostRequest(Command.CREATE, voucherType, value);
        final var responseMessage = frontController.request(requestMessage);

        output.printCreatedVoucher(responseMessage);
    }
}
