package com.prgrm.kdt.voucher.presentation;

import com.prgrm.kdt.command.CommandType;
import com.prgrm.kdt.view.InputView;
import com.prgrm.kdt.view.OutputView;
import com.prgrm.kdt.voucher.application.VoucherService;
import com.prgrm.kdt.voucher.domain.Voucher;
import com.prgrm.kdt.voucher.domain.VoucherType;

import java.util.List;
import java.util.Locale;

public class VoucherController implements Runnable {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void run() {
        while (true) {
            InputView.initVoucherMessage();
            String selectType = InputView.input();
            CommandType status = getType(selectType);
            executeMenu(status);
        }
    }

    private CommandType getType(String type) {
        return CommandType.getCommandType(type);
    }

    private void executeMenu(CommandType type) {
        if (type == CommandType.EXIT) {
            exitCommandOrder();
            return;
        }

        if (type == CommandType.CREATE) {
            createVoucherOrder();
            return;
        }

        if (type == CommandType.LIST) {
            showAllVoucherOrder();
        }
    }

    private void showAllVoucherOrder() {
        OutputView.showVoucherList(voucherService.findAllVoucher());
    }

    private void createVoucherOrder() {
        InputView.createVoucherMessage();
        VoucherType type = voucherService.selectVoucherType(InputView.input().toUpperCase(Locale.ROOT));
        InputView.enterVoucherDiscountMessage();
        long value = Long.parseLong(InputView.input());
        Voucher voucher = voucherService.createVoucher(type, value);
        voucherService.insertVoucher(voucher);
    }

    private void exitCommandOrder() {
        InputView.closeScanner();
        OutputView.exit();
    }
}
