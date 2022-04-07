package org.prgrms.springbootbasic.controller;

import java.util.List;
import org.prgrms.springbootbasic.entity.Voucher;
import org.prgrms.springbootbasic.service.VoucherService;
import org.prgrms.springbootbasic.service.VoucherType;
import org.prgrms.springbootbasic.view.ConsoleView;
import org.prgrms.springbootbasic.view.Menu;

public class VoucherController {

    private final VoucherService voucherService;
    private final ConsoleView consoleView;

    public VoucherController(VoucherService voucherService, ConsoleView consoleView) {
        this.voucherService = voucherService;
        this.consoleView = consoleView;
    }

    public void run() {
        while (true) {
            consoleView.printMenu();
            Menu menu = consoleView.inputMenu();

            if (menu == Menu.EXIT) {
                break;
            }
            if (menu == Menu.CREATE) {
                VoucherType voucherType = consoleView.selectVoucherType();
                if (voucherType == VoucherType.FIXED) {
                    long amount = consoleView.selectAmount();
                    voucherService.createFixedAmountVoucher(amount);
                } else {
                    int percent = consoleView.selectPercent();
                    voucherService.createPercentAmountVoucher(percent);
                }
            }
            if (menu == Menu.LIST) {
                List<Voucher> vouchers = voucherService.findAll();
                consoleView.printList(vouchers);
            }
        }
    }
}
