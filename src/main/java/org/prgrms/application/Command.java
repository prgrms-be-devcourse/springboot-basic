package org.prgrms.application;

import org.prgrms.application.controller.VoucherController;
import org.prgrms.application.view.InputView;
import org.prgrms.application.view.OutputView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Command implements CommandLineRunner {

    private final InputView inputView;
    private final OutputView outputView;
    private final VoucherController voucherController;
    private boolean isRunning = true;

    @Autowired
    public Command(InputView inputView, OutputView outputView, VoucherController voucherController) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.voucherController = voucherController;
    }

    @Override
    public void run(String... args) {
        while (isRunning) {
            outputView.printSelection();
            String selection = inputView.selectCommandType();
            Optional<CommandType> commandType = CommandType.findBySelection(selection);
            System.out.println(commandType);
            executeCommand(commandType.orElseThrow(() -> new RuntimeException("잘못된 입력입니다.")));
        }
    }

    private void executeCommand(CommandType commandType) {
        switch (commandType) {
            case CREATE -> {
                String voucherType = inputView.selectVoucherType();
                String voucherDetails = inputView.inputVoucherDetails();
                voucherController.createVoucher(voucherType, voucherDetails);
            }

            case LIST -> outputView.printStorageList(voucherController.getStorage());

            case EXIT -> isRunning = false;
        }
    }
}
