package me.programmers.springboot.basic.springbootbasic;

public class ApplicationController {

    private final CommandLineApplication commandLineApplication;

    ApplicationController(CommandLineApplication commandLineApplication) {
        this.commandLineApplication = commandLineApplication;
    }

    public void runCommandLineApp() {
        commandLineApplication.run();
    }
}
