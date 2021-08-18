package org.prgrms.kdt;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.prgrms.kdt.engine.VoucherProgram;

public class CommandLineApplication {
    public static void main(String[] args) {
        Console console = new Console();

        new VoucherProgram(console, console).run();
    }
}
