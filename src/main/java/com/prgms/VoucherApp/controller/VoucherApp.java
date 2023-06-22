package com.prgms.VoucherApp.controller;

import com.prgms.VoucherApp.domain.VoucherCreator;
import com.prgms.VoucherApp.domain.VoucherReader;
import com.prgms.VoucherApp.view.Command;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherApp implements Runnable {

    private final VoucherCreator voucherCreator;
    private final VoucherReader voucherReader;
    private final Input input;
    private final Output output;

    public VoucherApp(VoucherCreator voucherCreator, VoucherReader voucherReader, Input input, Output output) {
        this.voucherCreator = voucherCreator;
        this.voucherReader = voucherReader;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        while (true) {
            output.outputDisplayMenu();
            String inputCommand = input.inputCommand();
            Command command = Command.findByCommand(inputCommand);
            switch (command) {
                case EXIT:
                    return;
                case CREATE:
                    break;
                case LIST:
            }
        }
    }
}
