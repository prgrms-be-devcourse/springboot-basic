package org.prgrms.kdt.command;

import org.prgrms.kdt.blacklist.service.BlacklistService;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.voucher.service.MemoryVoucherService;

public class ExitCommand implements Command {
    @Override
    public boolean execute(Input input, Output output, MemoryVoucherService memoryVoucherService, BlacklistService blacklistService) { return false; }
}
