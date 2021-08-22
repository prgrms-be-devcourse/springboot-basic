package org.prgrms.kdt.command;

import org.prgrms.kdt.exception.InvalidIOMessageException;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.voucher.VoucherService;

public interface Command {
    void doCommands() throws InvalidIOMessageException;
}
