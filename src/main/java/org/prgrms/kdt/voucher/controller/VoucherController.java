package org.prgrms.kdt.voucher.controller;

import java.io.IOException;
import org.prgrms.kdt.app.configuration.io.InputHandler;
import org.prgrms.kdt.app.configuration.io.OutputHandler;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final VoucherService voucherService;
    private final String EXIT = "exit";
    private final String CREATE = "create";
    private final String LIST = "list";

    public VoucherController(InputHandler inputHandler, OutputHandler outputHandler, VoucherService voucherService) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.voucherService = voucherService;
    }

    public boolean startVoucherMenu() throws IOException {
        outputHandler.outputStartMessage();
        String menu = inputHandler.inputString();

        if(menu.equals(EXIT)){
            return false;
        }else if(menu.equals(CREATE)) {
            voucherService.createVoucher();
            return true;
        }else if(menu.equals(LIST)){
            voucherService.getAllVoucher();
            return true;
        }
    }
}
