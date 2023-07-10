package org.prgrms.application;

import org.prgrms.application.controller.VoucherController;
import org.prgrms.application.domain.voucher.VoucherType;
import org.prgrms.application.view.InputView;
import org.prgrms.application.view.OutputView;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Command implements CommandLineRunner {

    private final InputView inputView;
    private final OutputView outputView;
    private final VoucherController voucherController;
    private boolean isRunning = true;

    public Command(InputView inputView, OutputView outputView, VoucherController voucherController) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.voucherController = voucherController;
    }

    @Override
    public void run(String... args) {
        while (isRunning) {
            outputView.printSelect();
            CommandType commandType = inputView.selectCommandType();
            executeCommand(commandType);
        }
    }

    private void executeCommand(CommandType commandType) {
        switch (commandType) {
            case CREATE -> {
                outputView.printSelectVoucherType();
                VoucherType voucherType = inputView.selectVoucherType();
                double voucherDetail = inputView.inputVoucherDetails();
                voucherController.createVoucher(voucherType, voucherDetail);
            }

            case LIST -> outputView.printStorageList(voucherController.getStorage());

            case EXIT -> isRunning = false;
        }
    }
}
