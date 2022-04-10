package org.prgms.voucherProgram;

import org.prgms.voucherProgram.entity.MenuType;
import org.prgms.voucherProgram.entity.voucher.Voucher;
import org.prgms.voucherProgram.entity.voucher.VoucherType;
import org.prgms.voucherProgram.service.UserService;
import org.prgms.voucherProgram.service.VoucherService;
import org.prgms.voucherProgram.view.Console;
import org.prgms.voucherProgram.view.InputView;
import org.prgms.voucherProgram.view.OutputView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VoucherProgram {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final VoucherService voucherService;
    private final UserService userService;
    private final InputView inputView;
    private final OutputView outputView;

    public VoucherProgram(VoucherService voucherService, UserService userService, Console console) {
        this.voucherService = voucherService;
        this.userService = userService;
        this.inputView = console;
        this.outputView = console;
    }

    public void run() {
        boolean isNotEndProgram = true;

        while (isNotEndProgram) {
            MenuType menuType = inputMenu();
            logger.info("{} menu select", menuType);
            switch (menuType) {
                case EXIT -> isNotEndProgram = false;
                case LIST -> printVouchers();
                case CREATE -> {
                    VoucherType voucherType = inputVoucherCommand();
                    logger.info("{} select", voucherType);
                    Voucher voucher = createVoucher(voucherType);
                    outputView.printVoucher(voucher);
                }
                case BLACKLIST -> printBlackList();
            }
        }
    }

    private MenuType inputMenu() {
        while (true) {
            try {
                return MenuType.of(inputView.inputMenu());
            } catch (Exception e) {
                logger.error(e.getMessage());
                outputView.printError(e.getMessage());
            }
        }
    }

    private void printVouchers() {
        try {
            outputView.printVouchers(voucherService.findAllVoucher());
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            outputView.printError(e.getMessage());
        }
    }

    private VoucherType inputVoucherCommand() {
        while (true) {
            try {
                return VoucherType.findByCommand(inputView.inputVoucherCommand());
            } catch (Exception e) {
                logger.error(e.getMessage());
                outputView.printError(e.getMessage());
            }
        }
    }

    private Voucher createVoucher(VoucherType voucherType) {
        while (true) {
            try {
                long discountValue = inputView.inputDiscountValue(voucherType);
                return voucherService.create(voucherType, discountValue);
            } catch (Exception e) {
                logger.error(e.getMessage());
                outputView.printError(e.getMessage());
            }
        }
    }

    private void printBlackList() {
        try {
            outputView.printUsers(userService.findBlackList());
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            outputView.printError(e.getMessage());
        }
    }
}
