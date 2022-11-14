package org.prgrms.java.common;

import org.prgrms.java.domain.voucher.FixedAmountVoucher;
import org.prgrms.java.domain.voucher.PercentDiscountVoucher;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.exception.CommandException;
import org.prgrms.java.exception.UserException;
import org.prgrms.java.exception.VoucherException;
import org.prgrms.java.service.user.UserService;
import org.prgrms.java.service.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherProcessor implements CommandLineRunner { // 네이밍 변경
    private boolean isRunning = true;
    private final UserService userService;
    private final VoucherService voucherService;
    private final View view;
    private static final Logger logger = LoggerFactory.getLogger(VoucherProcessor.class);

    public VoucherProcessor(UserService userService, VoucherService voucherService, View view) {
        this.userService = userService;
        this.voucherService = voucherService;
        this.view = view;
    }

    @Override
    public void run(String... args) {
        while (isRunning) {
            try {
                processCommand();
            } catch (Exception e) {
                view.print(e.getMessage());
                logger.error(e.getMessage());
                isRunning = false;
            }
        }
    }

    private void processCommand() {
        view.print(MessageGuide.COMMAND_OPTION);
        try {
            switch (Command.get(view.read())) {
                case EXIT:
                    view.print("Terminates the program...");
                    isRunning = false;
                    break;
                case CREATE:
                    createVoucher();
                    break;
                case LIST:
                    getVouchers();
                    break;
                case BLACKLIST:
                    getBlackUsers();
            }
        } catch (CommandException e) {
            view.print(e.getMessage());
            logger.warn(e.getMessage());
        }
    }

    private void getBlackUsers() {
        try {
            userService.getAllBlackUser()
                    .forEach(view::print);
        } catch (UserException e) {
            view.print(e.getMessage());
            logger.warn(e.getMessage());
        }
    }

    private void createVoucher() {
        view.print(MessageGuide.VOUCHER_TYPE);
        String voucherType = view.read();

        view.print(MessageGuide.VOUCHER_DISCOUNT_AMOUNT);
        long voucherAmount;
        try {
            voucherAmount = Long.parseLong(view.read());
        } catch (IllegalArgumentException e) {
            view.print(e.getMessage());
            logger.warn(e.getMessage());
            return;
        }

        Voucher voucher;
        switch (voucherType) {
            case "1":
                voucher = new FixedAmountVoucher(UUID.randomUUID(), voucherAmount);
                break;
            case "2":
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), voucherAmount);
                break;
            default:
                view.print("Invalid Voucher Type");
                return;
        }

        try {
            voucherService.createVoucher(voucher);
        } catch (VoucherException e) {
            view.print(e.getMessage());
            logger.warn(e.getMessage());
        }
    }

    private void getVouchers() {
        voucherService.getAllVoucher()
                .forEach(view::print);
    }
}
