package org.prgrms.kdt.voucher.controller;

import java.io.IOException;
import org.prgrms.kdt.app.configuration.io.InputHandler;
import org.prgrms.kdt.app.configuration.io.OutputHandler;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import static org.prgrms.kdt.app.configuration.io.SystemMessage.*;

@Controller
public class VoucherController {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final VoucherService voucherService;
    private final String EXIT = "exit";
    private final String CREATE = "create";
    private final String LIST = "list";
    private final String FIXED = "fixed";
    private final String PERCENT = "percent";

    public VoucherController(InputHandler inputHandler, OutputHandler outputHandler, VoucherService voucherService) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.voucherService = voucherService;
    }

    public boolean startVoucherMenu() throws IOException {
        outputHandler.outputStartMessage();
        String menu = inputHandler.inputString();

        if(menu.equals(EXIT)){
            outputHandler.outputSystemMessage(EXIT_PROGRAM.getMessage());
            return false;
        }else if(menu.equals(CREATE)) {
            outputHandler.outputSystemMessage(CREATE_BOUCHER_TYPE.getMessage());
            String voucherType = inputHandler.inputString();

            if(voucherType.equals(FIXED)){

            }
            outputHandler.outputSystemMessage(CREATE_FIXED_BOUCHER.getMessage());
            outputHandler.outputSystemMessage(CREATE_PERCENT_BOUCHER.getMessage());
            Voucher voucher =
            voucherService.createVoucher(voucher);
            return true;
        }else if(menu.equals(LIST)){
            voucherService.getAllVouchers();
            return true;
        }
    }
}
