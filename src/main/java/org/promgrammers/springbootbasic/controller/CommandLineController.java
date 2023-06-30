package org.promgrammers.springbootbasic.controller;

import org.promgrammers.springbootbasic.domain.customer.model.CustomerType;
import org.promgrammers.springbootbasic.domain.customer.service.BlackCustomerService;
import org.promgrammers.springbootbasic.domain.voucher.dto.request.CreateVoucherRequest;
import org.promgrammers.springbootbasic.domain.voucher.model.Command;
import org.promgrammers.springbootbasic.domain.voucher.model.VoucherType;
import org.promgrammers.springbootbasic.domain.voucher.service.VoucherService;
import org.promgrammers.springbootbasic.view.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandLineController {

    private final BlackCustomerService blackCustomerService;
    private final VoucherService voucherService;
    private final Console console;

    private static final Logger logger = LoggerFactory.getLogger(CommandLineController.class);

    public CommandLineController(BlackCustomerService blackCustomerService, VoucherService voucherService, Console console) {
        this.blackCustomerService = blackCustomerService;
        this.voucherService = voucherService;
        this.console = console;
    }

    public void run() {
        while (CommandProgramStatus.isRunning()) {
            try {
                console.displayCommandGuide();
                String inputCommand = console.input();
                Command command = Command.of(inputCommand);
                execute(command);
            } catch (RuntimeException e) {
                logger.error(e.getMessage());
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
            case BLACKLIST -> console.print(blackCustomerService.findAllByCustomerType(CustomerType.BLACK).toString());
        }
    }

    private CreateVoucherRequest requestVoucherCreation() {
        String inputVoucherType = console.askForVoucherType();
        VoucherType voucherType = VoucherType.of(inputVoucherType);

        long discountAmount = console.askForDiscountAmount();
        return CreateVoucherRequest.of(voucherType, discountAmount);
    }
}
