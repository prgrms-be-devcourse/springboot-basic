package com.programmers.application.controller;

import com.programmers.application.io.IO;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

@Component
@Primary
public class FrontController implements Controller {
    private static final int ABNORMAL_EXIT = 1;
    private final Map<ServiceCommand, Controller> controllerMap = new EnumMap<>(ServiceCommand.class);
    private final VoucherController voucherController;
    private final IO io;

    //추후 고객, 지갑 컨트롤러 추가
    public FrontController(VoucherController voucherController, IO io) {
        this.voucherController = voucherController;
        this.io = io;
    }

    @Override
    public void process() throws IOException {
        try {
            printMenu();
            ServiceCommand serviceCommand = ServiceCommand.valueOf(io.read().toUpperCase());
            if (serviceCommand == ServiceCommand.EXIT) {
                System.exit(ABNORMAL_EXIT);
            }
            Controller controller = controllerMap.get(serviceCommand);
            controller.process();
        } catch (RuntimeException runtimeException) {
            io.write(runtimeException.getMessage());
        } catch (IOException ioException) {
            io.write(ioException.getMessage());
        }
    }

    private void printMenu() throws IOException {
        io.write("=== Program ===");
        io.write("Enter a voucher to use the voucher program");
        io.write("Enter a customer to use the customer program");
        io.write("Enter a wallet to use the wallet program");
        io.write("Enter a exit to exit the program.");

    }

    @PostConstruct
    private void initControllerMap() {
        controllerMap.put(ServiceCommand.VOUCHER, voucherController);
    }

    //추후 고객, 지갑 서비스 추가
    private enum ServiceCommand {
        VOUCHER, EXIT
    }
}
