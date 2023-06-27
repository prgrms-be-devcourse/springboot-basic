package programmers.org.voucher.controller;

import programmers.org.voucher.constant.Command;
import programmers.org.voucher.constant.VoucherType;
import programmers.org.voucher.domain.Voucher;
import programmers.org.voucher.io.ConsoleService;
import programmers.org.voucher.service.VoucherService;

import java.util.List;
import java.util.Optional;

public class VoucherController {

    private final VoucherService voucherService;
    private final ConsoleService consoleService;

    public VoucherController(VoucherService voucherService, ConsoleService consoleService) {
        this.voucherService = voucherService;
        this.consoleService = consoleService;
    }

    public void run() {
        while(true) {
            consoleService.printManual();
            String commandString = consoleService.inputCommand();
            Optional<Command> command = Command.find(commandString);

            if (command.isEmpty()) {
                consoleService.printInputCommandError();
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
        String voucherType = consoleService.inputVoucherType();
        Optional<VoucherType> findVoucherType = VoucherType.find(voucherType);

        if (findVoucherType.isEmpty()) {
            consoleService.printInputVoucherTypeError();
            return;
        }
        int voucherInfo = consoleService.inputVoucherInfo();
        voucherService.create(voucherInfo, findVoucherType.get());
    }

    private void printVoucherList() {
        List<Voucher> voucherList = voucherService.getAllVouchers();
        consoleService.printVoucherList(voucherList);
    }
}
