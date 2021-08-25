package com.prgms.kdtspringorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prgms.kdtspringorder.adapter.controller.CustomerController;
import com.prgms.kdtspringorder.adapter.controller.VoucherController;
import com.prgms.kdtspringorder.adapter.exception.InvalidDiscountException;
import com.prgms.kdtspringorder.domain.model.voucher.VoucherType;
import com.prgms.kdtspringorder.ui.Command;
import com.prgms.kdtspringorder.ui.Printer;
import com.prgms.kdtspringorder.ui.Receiver;

public class CommandLineApplication {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    private static final Receiver RECEIVER = new Receiver();
    private static final Printer PRINTER = new Printer();
    private final CustomerController customerController;
    private final VoucherController voucherController;

    public CommandLineApplication(CustomerController customerController, VoucherController voucherController) {
        this.customerController = customerController;
        this.voucherController = voucherController;
    }

    public void run() {
        findCustomerBlacklist();
        manageVouchers();
    }

    private void findCustomerBlacklist() {
        PRINTER.printBlacklist(customerController.findAllBlacklist());
    }

    private void manageVouchers() {
        PRINTER.printCommandList();

        while (true) {
            Command command = null;
            try {
                command = Command.valueOf(RECEIVER.enterCommand().toUpperCase());
            } catch (IllegalArgumentException e) {
                logger.error(e.getMessage());
                PRINTER.printInvalidCommandMessage();
                continue;
            }

            if (command.equals(Command.EXIT)) {
                break;
            }

            switch (command) {
                case CREATE:
                    createVoucher();
                    break;
                case LIST:
                    PRINTER.printVoucherList(voucherController.listVouchers());
                    break;
                case HELP:
                    PRINTER.printCommandList();
                    break;
                default:
                    break;
            }
        }
    }

    private void createVoucher() {
        try {
            VoucherType voucherType = VoucherType.valueOf(RECEIVER.enterVoucherType().toUpperCase());
            long discount = RECEIVER.enterDiscountAmount();
            voucherController.createVoucher(voucherType, discount);
        } catch (InvalidDiscountException e) {
            logger.error(e.getMessage());
            PRINTER.printInvalidDiscount(e.getMessage());   // 할인률이 100% 초과일 경우
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            PRINTER.printInvalidVoucherType(e.getMessage());    // 잘못된 voucher type을 입력했을 경우
        }
    }
}
