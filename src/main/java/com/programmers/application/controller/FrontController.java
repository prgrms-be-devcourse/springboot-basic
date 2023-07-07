package com.programmers.application.controller;

import com.programmers.application.io.IO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class FrontController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FrontController.class);

    private final Map<ServiceCommand, Controller> controllerMap;
    private final IO io;

    public FrontController(Map<ServiceCommand, Controller> controllerMap, IO io) {
        this.controllerMap = controllerMap;
        this.io = io;
    }

    public void process() throws IOException {
        try {
            printMenu();
            ServiceCommand serviceCommand = ServiceCommand.valueOf(io.read().toUpperCase());
            Controller controller = controllerMap.get(serviceCommand);
            controller.process();
        } catch (RuntimeException runtimeException) {
            io.write(runtimeException.getMessage());
        } catch (Exception exception) {
            LOGGER.error("Exception: {}", exception.getMessage());
        }
    }

    private void printMenu() throws IOException {
        io.write("=== Program ===");
        io.write("Enter a voucher to use the voucher program");
        io.write("Enter a customer to use the customer program");
        io.write("Enter a wallet to use the wallet program");
        io.write("Enter a exit to exit the program.");
    }
}
