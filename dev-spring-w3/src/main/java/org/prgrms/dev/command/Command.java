package org.prgrms.dev.command;

import org.prgrms.dev.io.Input;
import org.prgrms.dev.io.Output;
import org.prgrms.dev.voucher.service.VoucherService;

public interface Command {
    boolean execute(Input input, Output output, VoucherService voucherService);
}
