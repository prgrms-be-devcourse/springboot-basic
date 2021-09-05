package org.prgrms.kdt.domain.command;

import org.prgrms.kdt.io.IO;

import java.io.IOException;

public class ExitCommandAction implements CommandAction {
    private final IO io;

    public ExitCommandAction(IO io) {
        this.io = io;
    }

    @Override
    public void action() throws IOException{
        io.writeLine("Bye");
        System.exit(1);
    }
}
