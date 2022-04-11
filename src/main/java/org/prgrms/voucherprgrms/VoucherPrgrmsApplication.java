package org.prgrms.voucherprgrms;

import org.prgrms.voucherprgrms.io.InputConsole;
import org.prgrms.voucherprgrms.io.OutputConsole;
import org.prgrms.voucherprgrms.voucher.VoucherService;
import org.prgrms.voucherprgrms.voucher.model.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherPrgrmsApplication implements Runnable {

    private final VoucherService voucherService;
    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;

    public VoucherPrgrmsApplication(VoucherService voucherService, InputConsole inputConsole, OutputConsole outputConsole) {
        this.voucherService = voucherService;
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
    }

    @Override
    public void run() {
        boolean runnableFlag = true;
        while (runnableFlag) {
            String input = inputConsole.commandInput();
            switch (input) {
                case "exit":
                    runnableFlag = false;
                    break;
                case "create":
                    voucherService.createVoucher();
                    break;
                case "list":
                    List<Voucher> list = voucherService.findAllVoucher();
                    outputConsole.voucherList(list);
                    break;
                default:
                    outputConsole.commandErrorMessage();
            }
        }
    }


}
