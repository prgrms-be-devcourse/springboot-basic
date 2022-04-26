package com.waterfogsw.voucher.console;

import com.waterfogsw.voucher.console.handler.CommandHandler;
import com.waterfogsw.voucher.console.handler.CreateHandler;
import com.waterfogsw.voucher.console.handler.ListHandler;
import com.waterfogsw.voucher.global.FrontController;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleApplication implements ApplicationRunner {

    private final Input input;
    private final Output output;
    private final FrontController frontController;

    public ConsoleApplication(Input input, Output output, FrontController frontController) {
        this.input = input;
        this.output = output;
        this.frontController = frontController;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("=== Voucher Program ===");
        output.printCommandList();
        while (true) {
            try {
                Command command = input.inputCommand();
                if (command == Command.EXIT) {
                    output.printExitMessage();
                    break;
                }
                final CommandHandler commandHandler = handlerMapping(command);
                commandHandler.handle();
            } catch (IllegalArgumentException e) {
                output.printErrorMessage(e.getMessage());
            }
        }
    }

    private CommandHandler handlerMapping(Command command) {
        switch (command) {
            case CREATE -> {
                return new CreateHandler(input, output, frontController);
            }
            case LIST -> {
                return new ListHandler(input, output, frontController);
            }
            default -> throw new IllegalArgumentException();
        }
    }
}
