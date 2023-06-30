package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.voucher.model.Command;
import org.prgrms.kdt.voucher.model.Menu;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.prgrms.kdt.voucher.view.Input;
import org.prgrms.kdt.voucher.view.Output;
import org.springframework.stereotype.Controller;

import java.util.InputMismatchException;

import static org.prgrms.kdt.voucher.view.ConsoleOutput.SELECT_VOUCHER_MESSAGE;

@Controller
public class VoucherController {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public VoucherController(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    public void run() {
        while(true) {
            try {
                output.showMenu(Menu.values());
                Command command = new Command(input.getCommandInput());
                switch (Menu.of(command.getCommand())) {
                    case EXIT-> {
                        return;
                    }
                    case CREATE -> {
                        output.printMessage(SELECT_VOUCHER_MESSAGE);
                        output.showVoucherTypes(VoucherType.values());
                        String voucherTypeName = input.getVoucherInput();
                        output.printMessage(VoucherType.of(voucherTypeName).getBenefitMessage());
                        Long benefit = input.getBenefit();
                        voucherService.createVoucher(VoucherType.of(voucherTypeName), benefit);
                    }
                    case LIST -> {
                        output.showVoucherList(voucherService
                                .toVoucherDTOList());
                    }
                }
            } catch(IllegalArgumentException | InputMismatchException e) {
                e.printStackTrace();
            }
        }
    }
}
