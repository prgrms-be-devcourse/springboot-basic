package org.prgrms.application.controller;

import org.prgrms.application.view.InputView;
import org.prgrms.application.view.OutputView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandController implements CommandLineRunner{

    private final InputView inputView;
    private final OutputView outputView;
    private boolean isRunning = true;

    @Autowired
    public CommandController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
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
                V
            }

//            case LIST -> {
//
//            }

            case EXIT -> isRunning = false;
        }
    }
}
