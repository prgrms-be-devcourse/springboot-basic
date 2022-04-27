package com.waterfogsw.voucher.console;

import com.waterfogsw.voucher.global.FrontController;
import com.waterfogsw.voucher.global.GetRequest;
import com.waterfogsw.voucher.global.PostRequest;
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
    public void run(ApplicationArguments args) {
        output.display("=== Voucher Program ===");
        output.display(Command.getAllDescriptions());
        while (true) {
            try {
                final var command = input.inputCommand();
                if (!handleCommand(command)) return;
            } catch (IllegalArgumentException e) {
                output.display("Error: " + e.getMessage());
            }
        }
    }

    private boolean handleCommand(Command command) {
        switch (command) {
            case CREATE -> {
                output.display(VoucherType.getAllInputVoucherType());

                String voucherType = input.inputType();
                String value = input.inputValue();

                final var requestMessage = new PostRequest(Command.CREATE, voucherType, value);
                final var responseMessage = frontController.request(requestMessage);

                output.display(responseMessage);
            }
            case LIST -> {
                final var requestMessage = new GetRequest(Command.LIST);
                final var responseMessage = frontController.request(requestMessage);

                output.display(responseMessage);
            }
            case EXIT -> {
                output.display(Messages.EXIT_PROGRAM);
                return false;
            }
            default -> {
                throw new IllegalArgumentException();
            }
        }
        return true;
    }
}
