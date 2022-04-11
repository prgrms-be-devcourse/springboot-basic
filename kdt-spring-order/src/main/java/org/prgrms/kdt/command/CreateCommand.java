package org.prgrms.kdt.command;

import org.prgrms.kdt.CommandLine;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(CreateCommand.class);

    @Override
    public boolean runCommand(Input input, Output output) {
        output.selectVoucherType();
        String voucherType = input.input("> ");
        System.out.println(voucherType);
        return true;
    }
}
