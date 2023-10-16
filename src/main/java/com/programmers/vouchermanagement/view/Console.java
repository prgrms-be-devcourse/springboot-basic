package com.programmers.vouchermanagement.view;

import com.programmers.vouchermanagement.common.ConsoleMessage;
import com.programmers.vouchermanagement.domain.VoucherType;
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
            commandMap.get(getCommand()).run();
        }
    }

    private String getCommand() {
        displayMessage(ConsoleMessage.COMMAND_LIST_MESSAGE.getMessage());
        Command command = textIO.newEnumInputReader(Command.class)
                .withPossibleValues()
                .read();
        return command.toString();
    }

    private void createVoucher() {
        VoucherType voucherType = textIO.newEnumInputReader(VoucherType.class)
                .withAllValues()
                .read();
        String voucherName = textIO.newStringInputReader()
                .withMinLength(1)
                .read("Voucher Name: ");
        float discountAmount = textIO.newFloatInputReader()
                .withMinVal(0f)
                .read("Discount Amount: ");

        voucherService.createVoucher(voucherName, discountAmount, voucherType);
    }

    private void displayVoucherList() {
        voucherService.voucherList()
                .forEach(voucher -> displayMessage(voucher.toString()));
    }

    private void exit() {
        displayMessage(ConsoleMessage.EXIT_MESSAGE.getMessage());
        System.exit(0);
    }

    private void displayMessage(String message) {
        textIO.getTextTerminal().println(message);
    }
}
