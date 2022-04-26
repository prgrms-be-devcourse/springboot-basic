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
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("=== Voucher Program ===");
        output.printCommandList();
        while (true) {
            try {
                final var command = input.inputCommand();
                if (command == Command.EXIT) {
                    output.printExitMessage();
                    break;
                }
                handleCommand(command);
            } catch (IllegalArgumentException e) {
                output.printErrorMessage(e.getMessage());
            }
        }
    }

    private void handleCommand(Command command) {
        switch (command) {
            case CREATE -> {
                output.printVoucherTypes();

                String voucherType = input.inputType();
                String value = input.inputValue();

                final var requestMessage = new PostRequest(Command.CREATE, voucherType, value);
                final var responseMessage = frontController.request(requestMessage);

                output.printCreatedVoucher(responseMessage);
            }
            case LIST -> {
                final var requestMessage = new GetRequest(Command.LIST);
                final var responseMessage = frontController.request(requestMessage);

                output.printAllVoucher(responseMessage);
            }
            default -> {
                throw new IllegalArgumentException();
            }
        }
    }
}
