package org.prgrms.kdt;

import org.prgrms.kdt.io.IO;
import org.prgrms.kdt.model.CommandType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import static org.prgrms.kdt.utils.Constant.COMMAND_ERROR_PROMPT;

@Configuration
public class VoucherManagementController implements CommandLineRunner {
    private final IO io;

    public VoucherManagementController(IO io) {
        this.io = io;
    }


    @Override
    public void run(String... args) throws Exception {
        while (true) {
            io.outputCommands();

            String userInput = io.getInput();

            switch (CommandType.findCommandType(userInput)) {
                case CREATE -> {
                }
                case LIST -> {
                }
                case EXIT -> {
                    return;
                }
                case UNKNOWN -> io.doOutput(COMMAND_ERROR_PROMPT);
                default -> {}
            }
        }
    }
}
