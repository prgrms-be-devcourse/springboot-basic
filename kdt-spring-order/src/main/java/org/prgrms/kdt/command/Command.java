package org.prgrms.kdt.command;

import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.io.Input;

public interface Command {
    boolean runCommand(Input input, Output output);
    // boolean runCommand(String input, Output output, VoucherService voucherService);
}
