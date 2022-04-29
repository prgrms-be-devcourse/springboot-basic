package org.programmers.kdtspring.ConsoleIO;

public class ExitCommandStrategy implements CommandStrategy {

    private final Output output;

    public ExitCommandStrategy(Output output) {
        this.output = output;
    }

    @Override
    public void runCommand() {
        output.terminate();
    }
}
