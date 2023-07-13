package programmers.org.voucher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import programmers.org.voucher.constant.Command;
import programmers.org.voucher.dto.VoucherRequest;
import programmers.org.voucher.dto.VoucherResponse;
import programmers.org.voucher.io.VoucherConsole;
import programmers.org.voucher.service.VoucherService;

import java.util.List;

@Component
public class VoucherController implements CommandLineRunner {

    private final static Logger logger = LoggerFactory.getLogger(VoucherController.class);

    private final VoucherService voucherService;
    private final VoucherConsole voucherConsole;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
        this.voucherConsole = new VoucherConsole();
    }

    @Override
    public void run(String... args) {
        boolean isRunning = true;

        while (isRunning) {
            voucherConsole.printManual();
            String inputCommand = voucherConsole.inputCommand();

            Command command = Command.find(inputCommand);

            switch (command) {
                case CREATE:
                    createVoucher();
                    break;
                case LIST:
                    printVoucherList();
                    break;
                case FIND:
                    printFoundVoucher();
                    break;
                case UPDATE:
                    updateVoucher();
                    break;
                case EXIT:
                    isRunning = false;
            }
        }
    }

    private void createVoucher() {
        String voucherType = voucherConsole.inputVoucherType();
        int discountAmount = voucherConsole.inputVoucherInfo();

        VoucherRequest request = new VoucherRequest(discountAmount, voucherType);
        voucherService.create(request);
    }

    private void printVoucherList() {
        List<VoucherResponse> voucherList = voucherService.getAllVouchers();
        voucherConsole.printVoucherList(voucherList);
    }

    private void printFoundVoucher() {
        long voucherId = voucherConsole.inputVoucherId();
        VoucherResponse response = voucherService.getVoucher(voucherId);
        voucherConsole.printVoucher(response);
    }

    private void updateVoucher() {
        long voucherId = voucherConsole.inputVoucherId();
        int discountAmount = voucherConsole.inputVoucherInfo();

        VoucherRequest request = new VoucherRequest(discountAmount, null);
        voucherService.update(voucherId, request);
    }
}
