package org.prgrms.kdt.command;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;

public class ExitCommand implements Command {
    @Override
    public boolean runCommand(Input input, Output output) { return false; }
}
