package org.prgrms.kdt.command;

import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.voucher.service.VoucherService;

public interface Command {
    boolean execute(Input input, Output output, VoucherService voucherService);
    // boolean runCommand(String input, Output output, VoucherService voucherService);
}
