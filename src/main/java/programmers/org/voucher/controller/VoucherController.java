package programmers.org.voucher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import programmers.org.voucher.constant.Command;
import programmers.org.voucher.constant.VoucherType;
import programmers.org.voucher.domain.Voucher;
import programmers.org.voucher.io.Input;
import programmers.org.voucher.io.Output;
import programmers.org.voucher.io.VoucherConsole;
import programmers.org.voucher.service.VoucherService;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public class VoucherController implements CommandLineRunner {

    private final static Logger logger = LoggerFactory.getLogger(VoucherController.class);

    private final VoucherService voucherService;
    private final VoucherConsole voucherConsole;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
        this.voucherConsole = new VoucherConsole(new Input(), new Output());
    }

    @Override
    public void run(String... args) {
        run();
    }

    public void run() {
        boolean isExit = false;

        while (!isExit) {
            voucherConsole.printManual();
            String inputCommand = voucherConsole.inputCommand();

            try {
                Command command = Command.find(inputCommand);
                switch (command) {
                    case CREATE:
                        createVoucher();
                        break;
                    case LIST:
                        printVoucherList();
                        break;
                    case EXIT:
                        isExit = true;
                }
            } catch (NoSuchElementException e) {
                logger.warn(e.getMessage());
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
            logger.warn(e.getMessage());
        }
    }

    private void printVoucherList() {
        List<Voucher> voucherList = voucherService.getAllVouchers();
        voucherConsole.printVoucherList(voucherList);
    }
}
