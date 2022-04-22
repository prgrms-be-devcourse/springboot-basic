package org.prgms.management.command;

import org.prgms.management.blacklist.service.BlacklistService;
import org.prgms.management.io.Input;
import org.prgms.management.io.Output;
import org.prgms.management.voucher.service.VoucherService;

public class ExitCommand implements Command {
    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService, BlacklistService blacklistService) {
        return false;
    }
}
