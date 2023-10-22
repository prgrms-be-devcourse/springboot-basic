package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.app.configuration.io.InputHandler;
import org.prgrms.kdt.app.configuration.io.OutputHandler;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final VoucherService voucherService;

    public VoucherController(InputHandler inputHandler, OutputHandler outputHandler, VoucherService voucherService) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.voucherService = voucherService;
    }

    public void


}
