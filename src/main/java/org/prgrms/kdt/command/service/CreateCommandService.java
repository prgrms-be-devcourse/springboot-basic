package org.prgrms.kdt.command.service;

import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateCommandService implements Command {

    private static final String INPUT_PROMPT = "> ";

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public CreateCommandService(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    @Override
    public boolean execute() {
        // input voucher Type
        output.printRequestVoucherType();
        String inputVoucherType = input.inputString(INPUT_PROMPT);
        VoucherType voucherType = VoucherType.findVoucher(inputVoucherType);

        // input Voucher Value
        output.printRequestVoucherValue(voucherType);
        long value = Long.parseLong(input.inputString(INPUT_PROMPT));

        // create Voucher Instance
        voucherService.createVoucher(
                voucherType,
                UUID.randomUUID(),
                value);
        return true;
    }
}
