package org.promgrammers.springbootbasic.controller;

import org.promgrammers.springbootbasic.domain.VoucherType;
import org.promgrammers.springbootbasic.dto.request.CreateVoucherRequest;
import org.promgrammers.springbootbasic.service.VoucherService;
import org.promgrammers.springbootbasic.view.Command;
import org.promgrammers.springbootbasic.view.Console;
import org.springframework.stereotype.Component;

@Component
public class CommandLineController {

    private final VoucherService voucherService;
    private final Console console;

    private static final String[] COMMAND_GUIDE_MESSAGES = {
            "=== Voucher Program ===",
            "Type 'exit' to exit the program.",
            "Type 'create' to create a new voucher.",
            "Type 'list' to list all vouchers."
    };

    public CommandLineController(VoucherService voucherService, Console console) {
        this.voucherService = voucherService;
        this.console = console;
    }

    public void run() {
        while (CommandProgramStatus.isRunning()) {
            try {
                displayCommandGuide();
                String inputCommand = console.input();
                Command command = Command.of(inputCommand);
                execute(command);
            } catch (RuntimeException e) {
                console.print(e.getMessage());
            }
        }
    }

    private void execute(Command command) {
        switch (command) {
            case CREATE -> {
                CreateVoucherRequest requestVoucher = requestVoucherCreation();
                console.print("Voucher가 생성되었습니다. : " + voucherService.create(requestVoucher).toString());
            }
            case LIST -> console.print(voucherService.findAll().toString());
            case EXIT -> CommandProgramStatus.stop();
        }
    }

    private void displayCommandGuide() {
        console.print(COMMAND_GUIDE_MESSAGES);
    }

    private CreateVoucherRequest requestVoucherCreation() {
        String inputVoucherType = console.askForVoucherType();
        VoucherType voucherType = VoucherType.of(inputVoucherType);

        long discountAmount = console.askForDiscountAmount();
        return CreateVoucherRequest.of(voucherType, discountAmount);
    }
}
