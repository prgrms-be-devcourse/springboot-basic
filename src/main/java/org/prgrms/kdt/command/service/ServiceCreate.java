package org.prgrms.kdt.command.service;

import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.io.Console;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ServiceCreate implements Command {

    private static final String INPUT_PROMPT = "> ";

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public ServiceCreate(Console console, VoucherService voucherService) {
        this.input = console;
        this.output = console;
        this.voucherService = voucherService;
    }

    @Override
    public boolean execute() {
        output.inputVoucherType();
        String inputVoucherType = input.inputString(INPUT_PROMPT);
        VoucherType voucherType = VoucherType.findVoucher(inputVoucherType);

        output.inputVoucherValue(voucherType);
        long value = Long.parseLong(input.inputString(INPUT_PROMPT)); // TODO: 예외 처리,,, how to 잘?

        voucherService.createVoucher(
                voucherType,
                UUID.randomUUID(),
                value);
        return true;
    }
}
