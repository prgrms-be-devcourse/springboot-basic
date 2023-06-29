package programmers.org.voucher.controller;

import org.springframework.stereotype.Component;
import programmers.org.voucher.constant.Command;
import programmers.org.voucher.constant.VoucherType;
import programmers.org.voucher.domain.Voucher;
import programmers.org.voucher.io.VoucherConsole;
import programmers.org.voucher.service.VoucherService;

import java.util.List;
import java.util.Optional;

import static programmers.org.voucher.exception.ErrorMessage.COMMAND_ERROR_MESSAGE;
import static programmers.org.voucher.exception.ErrorMessage.VOUCHER_ERROR_MESSAGE;

@Component
public class VoucherController {

    private final VoucherService voucherService;
    private final VoucherConsole voucherConsole;

    public VoucherController(VoucherService voucherService, VoucherConsole voucherConsole) {
        this.voucherService = voucherService;
        this.voucherConsole = voucherConsole;
    }

    public void run() {
        while(true) {
            voucherConsole.printManual();
            String commandString = voucherConsole.inputCommand();
            Optional<Command> command = Command.find(commandString);

            if (command.isEmpty()) {
                voucherConsole.printError(COMMAND_ERROR_MESSAGE.getMessage());
                continue;
            }

            switch (command.get()) {
                case CREATE:
                    createVoucher();
                    break;
                case LIST:
                    printVoucherList();
                    break;
                case EXIT:
                    return;
            }
        }
    }

    private void createVoucher() {
        String voucherType = voucherConsole.inputVoucherType();
        Optional<VoucherType> findVoucherType = VoucherType.find(voucherType);

        if (findVoucherType.isEmpty()) {
            voucherConsole.printError(VOUCHER_ERROR_MESSAGE.getMessage());
            return;
        }
        int voucherInfo = voucherConsole.inputVoucherInfo();
        voucherService.create(voucherInfo, findVoucherType.get());
    }

    private void printVoucherList() {
        List<Voucher> voucherList = voucherService.getAllVouchers();
        voucherConsole.printVoucherList(voucherList);
    }
}
