package org.prgrms.kdtspringhomework.command;

import org.prgrms.kdtspringhomework.io.Input;
import org.prgrms.kdtspringhomework.io.Output;
import org.prgrms.kdtspringhomework.voucher.service.VoucherService;

@FunctionalInterface
public interface CommandStrategy {
    boolean execute(Input input, Output ouput, VoucherService voucherService);
}
