package org.prgrms.deukyun.voucherapp.app.command;

import org.springframework.shell.ExitRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ExitCommand {

    @ShellMethod(value = "exit voucher app", key = "exit")
    public void exit() {
        throw new ExitRequest();
    }
}
