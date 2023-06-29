package programmers.org.voucher.controller;

import org.springframework.stereotype.Component;
import programmers.org.voucher.constant.Command;
import programmers.org.voucher.constant.VoucherType;
import programmers.org.voucher.domain.Voucher;
import programmers.org.voucher.io.VoucherConsole;
import programmers.org.voucher.service.VoucherService;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public class VoucherController {

    private final VoucherService voucherService;
    private final VoucherConsole voucherConsole;

    public VoucherController(VoucherService voucherService, VoucherConsole voucherConsole) {
        this.voucherService = voucherService;
        this.voucherConsole = voucherConsole;
    }

    public void run() {
        while (true) {
            voucherConsole.printManual();
            String commandString = voucherConsole.inputCommand();

            try {
                Command command = Command.find(commandString);
                switch (command) {
                    case CREATE:
                        createVoucher();
                        break;
                    case LIST:
                        printVoucherList();
                        break;
                    case EXIT:
                        return;
                }
            } catch (NoSuchElementException e) {
                voucherConsole.printError(e.getMessage());
            }
        }
    }

    private void createVoucher() {
        String voucherType = voucherConsole.inputVoucherType();

        try {
            VoucherType findVoucherType = VoucherType.find(voucherType);
            int voucherInfo = voucherConsole.inputVoucherInfo();
            voucherService.create(voucherInfo, findVoucherType);

        } catch (NoSuchElementException e) {
            voucherConsole.printError(e.getMessage());
        }
    }

    private void printVoucherList() {
        List<Voucher> voucherList = voucherService.getAllVouchers();
        voucherConsole.printVoucherList(voucherList);
    }
}
