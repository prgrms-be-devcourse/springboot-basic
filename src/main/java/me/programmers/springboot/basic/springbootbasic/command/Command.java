package me.programmers.springboot.basic.springbootbasic.command;

public class Command {

    private final CommandStrategy strategy;

    public Command(CommandStrategy strategy) {
        this.strategy = strategy;
    }

    public void operateCommand() {
        strategy.operateCommand();
    }
}
