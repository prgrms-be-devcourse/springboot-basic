package org.prgrms.vouchermanager.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class VoucherSpringShell {

    @ShellMethod(value = "Add two intergers together")
    public int add(int a, int b) {
        return a + b;
    }
}
