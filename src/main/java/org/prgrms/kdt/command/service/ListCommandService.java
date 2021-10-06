package org.prgrms.kdt.command.service;

import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListCommandService implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ListCommandService.class);

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public ListCommandService(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    @Override
    public boolean execute() {
        List<Voucher> vouchers = voucherService.getAllVoucher();

        String voucherList = vouchers.stream()
                .map(Voucher::toString)
                .collect(Collectors.joining());

        logger.info("Execute Voucher List : {}", voucherList);

        output.printVoucherList(vouchers);

        return true;
    }
}
