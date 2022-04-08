package org.prgrms.springbootbasic.controller;

import java.util.List;
import org.prgrms.springbootbasic.entity.Voucher;
import org.prgrms.springbootbasic.service.VoucherService;
import org.prgrms.springbootbasic.service.VoucherType;
import org.prgrms.springbootbasic.view.ConsoleView;
import org.prgrms.springbootbasic.view.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private final VoucherService voucherService;
    private final ConsoleView consoleView;

    public VoucherController(VoucherService voucherService, ConsoleView consoleView) {
        this.voucherService = voucherService;
        this.consoleView = consoleView;
    }

    public void run() {
        while (process())
            ;
    }

    private boolean process() {

        consoleView.printMenu();
        Menu menu = consoleView.inputMenu();

        if (menu.isExit()) {
            return false;
        }

        if (menu.isCreate()) {
            createVoucher();
        }

        if (menu.isList()) {
            showList();
        }

        return true;
    }

    private void showList() {

        logger.info("VoucherController.showList() called");

        List<Voucher> vouchers = voucherService.findAll();
        consoleView.printList(vouchers);
    }

    private void createVoucher() {

        logger.info("VoucherController.createVoucher() called");

        VoucherType voucherType = consoleView.selectVoucherType();
        if (voucherType == VoucherType.FIXED) {
            long amount = consoleView.selectAmount();
            voucherService.createFixedAmountVoucher(amount);
        } else {
            int percent = consoleView.selectPercent();
            voucherService.createPercentAmountVoucher(percent);
        }
    }
}
