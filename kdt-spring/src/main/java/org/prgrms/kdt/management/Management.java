package org.prgrms.kdt.management;

import org.prgrms.kdt.domain.command.Command;

import java.io.IOException;

public interface Management {
    void run() throws IOException;
    void execute(Command command) throws IOException;
    void printInitMenu() throws IOException;
}
