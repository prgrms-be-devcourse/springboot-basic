package org.prgrms.java.controller.voucher;

import org.prgrms.java.common.Command;
import org.prgrms.java.common.MessageGuide;
import org.prgrms.java.controller.io.IOController;
import org.prgrms.java.domain.voucher.FixedAmountVoucher;
import org.prgrms.java.domain.voucher.PercentDiscountVoucher;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.service.user.UserService;
import org.prgrms.java.service.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class VoucherController implements CommandLineRunner {
    private boolean isRunning = true;
    private final UserService userService;
    private final VoucherService voucherService;
    private final IOController ioController;
    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    public VoucherController(UserService userService, VoucherService voucherService, IOController ioController) {
        this.userService = userService;
        this.voucherService = voucherService;
        this.ioController = ioController;
    }

    @Override
    public void run(String... args) {
        while (isRunning) {
            try {
                ioController.write(MessageGuide.COMMAND_OPTION);
                switch (Command.get(ioController.read())) {
                    case EXIT:
                        ioController.write("Terminates the program...");
                        isRunning = false;
                        break;
                    case CREATE:
                        createVoucher();
                        break;
                    case LIST:
                        getVouchers();
                        break;
                    case BLACK_LIST:
                        getBlackUsers();
                }
            } catch (Exception e) {
                ioController.write(e.getMessage());
                logger.error(e.getMessage());
            }
        }
    }

    private void getBlackUsers() {
        userService.getAllBlackUser()
                .forEach(ioController::write);
    }

    private void createVoucher() {
        ioController.write(MessageGuide.VOUCHER_TYPE);
        String voucherType = ioController.read();

        ioController.write(MessageGuide.VOUCHER_DISCOUNT_AMOUNT);
        long voucherAmount = Long.parseLong(ioController.read());

        Voucher voucher;
        switch (voucherType) {
            case "1":
                voucher = new FixedAmountVoucher(UUID.randomUUID(), voucherAmount);
                break;
            case "2":
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), voucherAmount);
                break;
            default:
                ioController.write("Invalid Voucher Type");
                return;
        }

        voucherService.createVoucher(voucher);
    }

    private void getVouchers() {
        voucherService.getAllVoucher()
                .forEach(ioController::write);
    }
}
