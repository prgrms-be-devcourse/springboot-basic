package com.prgrm.kdt.voucher.presentation;

import com.prgrm.kdt.command.CommandType;
import com.prgrm.kdt.view.InputView;
import com.prgrm.kdt.view.OutputView;
import com.prgrm.kdt.voucher.application.VoucherService;

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
            return;
        }
    }

    private void showAllVoucherOrder() {
        OutputView.showVoucherList(voucherService.findAllVoucher());
    }

    private void createVoucherOrder() {
    }

    private void exitCommandOrder() {
        InputView.closeScanner();
        OutputView.exit();
    }
}
