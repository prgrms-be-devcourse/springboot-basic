package com.waterfogsw.voucher.console.handler;

import com.waterfogsw.voucher.console.Command;
import com.waterfogsw.voucher.console.Input;
import com.waterfogsw.voucher.console.Output;
import com.waterfogsw.voucher.global.FrontController;
import com.waterfogsw.voucher.global.GetRequest;

public class ListHandler extends CommandHandler {
    public ListHandler(Input input, Output output, FrontController frontController) {
        super(input, output, frontController);
    }

    @Override
    public void handle() {
        final var requestMessage = new GetRequest(Command.LIST);
        final var responseMessage = frontController.request(requestMessage);
        output.printAllVoucher(responseMessage);
    }
}
