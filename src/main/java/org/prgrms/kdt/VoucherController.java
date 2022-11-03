package org.prgrms.kdt;

import org.prgrms.kdt.io.IO;
import org.prgrms.kdt.model.CommandType;
import org.prgrms.kdt.utils.VoucherControllerManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VoucherController implements CommandLineRunner {

    private final IO io;
    private final VoucherControllerManager voucherManager;

    public VoucherController(IO io, VoucherControllerManager voucherManager) {
        this.io = io;
        this.voucherManager = voucherManager;
    }

    @Override
    public void run(String... args) throws Exception {
        while (voucherManager.isRunning()) {
            io.outputCommands();

            String userInput = io.getInput();

            switch (CommandType.findCommandType(userInput)) {
                case CREATE -> {
                }
                case LIST -> {
                }
                case EXIT -> voucherManager.isRunning();
                case UNKNOWN -> io.outputCommandError();
                default -> {
                }
            }
        }
    }
}
