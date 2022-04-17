package org.prgrms.voucherprgrms;

import org.prgrms.voucherprgrms.io.InputConsole;
import org.prgrms.voucherprgrms.io.OutputConsole;
import org.prgrms.voucherprgrms.voucher.VoucherService;
import org.prgrms.voucherprgrms.voucher.model.Voucher;
import org.prgrms.voucherprgrms.voucher.model.VoucherDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherPrgrmsApplication implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(VoucherPrgrmsApplication.class);

    private final VoucherService voucherService;
    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;

    public VoucherPrgrmsApplication(VoucherService voucherService, InputConsole inputConsole, OutputConsole outputConsole) {
        this.voucherService = voucherService;
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        boolean runnableFlag = true;
        while (runnableFlag) {
            String input = inputConsole.commandInput();
            try {
                switch (input) {
                    case "exit":
                        runnableFlag = false;
                        break;
                    case "create":
                        createVoucher();
                        break;
                    case "list":
                        printList();
                        break;
                    default:
                        outputConsole.commandErrorMessage();
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                outputConsole.commandErrorMessage();
            }
        }
    }

    private void printList() {
        List<Voucher> list = voucherService.findAllVoucher();
        outputConsole.voucherList(list);
    }

    private void createVoucher() {
        String voucherType = inputConsole.getVoucherType();
        long value = inputConsole.getVoucherValue();

        voucherService.createVoucher(new VoucherDTO(voucherType, value));
    }


}
