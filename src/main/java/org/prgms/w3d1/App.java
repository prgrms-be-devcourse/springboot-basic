package org.prgms.w3d1;

import org.prgms.w3d1.io.Console;

public class App {
    public static void main(String[] args) {
        Console console = new Console();
        new CommandLineApplication(console, console).run();
    }
}
