package org.prgrms.springbootbasic.controller;

import org.prgrms.springbootbasic.VoucherType;
import org.prgrms.springbootbasic.service.VoucherService;
import org.prgrms.springbootbasic.view.ConsoleView;
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
        while (process()) {
        }
    }

    private boolean process() {

        logger.info("VoucherController.process() called");

        consoleView.printMenu();
        Menu menu = consoleView.inputMenu();
        return menu.apply(this);
    }

    public void createVoucher() {

        VoucherType voucherType = consoleView.selectVoucherType();
        if (voucherType.isFixed()) {
            long amount = consoleView.selectAmount();
            voucherService.createFixedAmountVoucher(amount);
        }

        if (voucherType.isPercent()) {
            int percent = consoleView.selectPercent();
            voucherService.createPercentAmountVoucher(percent);
        }
    }

    public void printList() {
        consoleView.printList(voucherService.findAll());
    }

    public void printBlackList() {
        consoleView.printCustomerBlackList();
    }
}
