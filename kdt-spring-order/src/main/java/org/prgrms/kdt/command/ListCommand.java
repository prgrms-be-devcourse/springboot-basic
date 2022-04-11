package org.prgrms.kdt.command;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;

public class ListCommand implements Command {
    @Override
    public boolean runCommand(Input input, Output output) {
        return true;
    }
}
