package org.prgrms.kdt.command;

import org.prgrms.kdt.exception.InvalidIOMessageException;

public interface Command {
    void doCommands() throws InvalidIOMessageException;
}
