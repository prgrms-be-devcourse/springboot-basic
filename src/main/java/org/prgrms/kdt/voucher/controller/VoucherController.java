package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.app.configuration.io.InputHandler;
import org.prgrms.kdt.app.configuration.io.OutputHandler;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.UUID;

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
        var menu = inputHandler.inputString();

        if(menu.equals(EXIT)) {
            outputHandler.outputSystemMessage(EXIT_PROGRAM.getMessage());
            return false;
        }
        else if(menu.equals(CREATE))
        {
            outputHandler.outputSystemMessage(CREATE_BOUCHER_TYPE.getMessage());
            var createVoucherType = inputHandler.inputString();

            if(createVoucherType.equals(FIXED)){
                outputHandler.outputSystemMessage(CREATE_FIXED_BOUCHER.getMessage());

                var amount = 0;
                while(true) {
                    amount = inputHandler.inputInt();

                    if(amount <= 0){
                        outputHandler.outputSystemMessage(EXCEPTION_FIXED_AMOUNT_MINUS.getMessage());
                        continue;
                    }
                    if(amount >= 100_000){
                        outputHandler.outputSystemMessage(EXCEPTION_FIXED_AMOUNT_OVER.getMessage());
                        continue;
                    }
                    break;
                }

                var fixedAmountVoucherDto = new FixedAmountVoucherDto(UUID.randomUUID(), amount);
                voucherService.createVoucher(fixedAmountVoucherDto);
            }

//            outputHandler.outputSystemMessage(CREATE_PERCENT_BOUCHER.getMessage());

            return true;
        }
        else if(menu.equals(LIST))
        {
            voucherService.getAllVouchers();
            return true;
        }
        else{
            outputHandler.outputSystemMessage(EXCEPTION_VOUCHER_TYPE.getMessage());
            return false;
        }
    }
}
