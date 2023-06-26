package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.voucher.model.Command;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.prgrms.kdt.voucher.view.Input;
import org.prgrms.kdt.voucher.view.Output;

public class Controller {
    // VoucherRepository로부터 Controller로 DTO로 받는다..?
    private final Input input;
    private final Output output;
    private final VoucherRepository voucherRepository;

    public Controller(Input input, Output output, VoucherRepository voucherRepository) {
        this.input = input;
        this.output = output;
        this.voucherRepository = voucherRepository;
    }

    public void run() {
        while(true) {
            Command command = new Command(input.getCommandInput());
//            switch ()
        }
    }
}
