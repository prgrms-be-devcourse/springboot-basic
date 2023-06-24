package org.prgrms.application;

import org.prgrms.application.controller.VoucherController;
import org.prgrms.application.view.InputView;
import org.prgrms.application.view.OutputView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Command implements CommandLineRunner{

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
    public void run(String... args) throws Exception {
        while(isRunning) {
            outputView.printSelection();
            String selection = inputView.selectCommandType();
            CommandType commandType = CommandType.valueOf(selection);
            selectCommand(commandType);
        }
    }

    private void selectCommand(CommandType commandType){
        switch (commandType){
            case CREATE -> {
                String selection = inputView.selectVoucherType();

            }

//            case LIST -> {
//
//            }

            case EXIT -> isRunning = false;
        }
    }
}
