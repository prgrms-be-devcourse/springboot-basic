package com.programmers.vouchermanagement.view;

import com.programmers.vouchermanagement.common.ConsoleMessage;
import com.programmers.vouchermanagement.service.VoucherService;
import jakarta.annotation.PostConstruct;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Console implements CommandLineRunner {

    private final VoucherService voucherService;
    private final Map<String, Runnable> commandMap = new HashMap<>();
    private final TextIO textIO = TextIoFactory.getTextIO();

    Console(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostConstruct
    private void init() {
        commandMap.put("exit", this::exit);
        commandMap.put("create", this::createVoucher);
        commandMap.put("list", this::displayVoucherList);
    }

    @Override
    public void run(String... args) {
        while (true) {
            try {
                commandMap.get(getCommand()).run();
            } catch (NullPointerException e) {
                textIO.getTextTerminal().println(ConsoleMessage.INVALID_COMMAND_MESSAGE.getMessage());
            }
        }
    }

    private String getCommand() {
        textIO.getTextTerminal().println(ConsoleMessage.COMMAND_LIST_MESSAGE.getMessage());
        return textIO.newStringInputReader()
                .read(">");

    }

    private void exit() {
        textIO.getTextTerminal().println(ConsoleMessage.EXIT_MESSAGE.getMessage());
        System.exit(0);
    }

    private void createVoucher() {
        voucherService.createVoucher();
    }

    private void displayVoucherList() {
        voucherService.voucherList();
    }
}
