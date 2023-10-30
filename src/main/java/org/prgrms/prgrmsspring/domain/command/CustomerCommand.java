package org.prgrms.prgrmsspring.domain.command;

import org.prgrms.prgrmsspring.console.ConsoleIOManager;
import org.prgrms.prgrmsspring.console.CustomerConsole;

import java.util.function.Consumer;

public enum CustomerCommand implements Command {
    CREATE("create a new customer", CustomerConsole::create),
    UPDATE("update a customer", CustomerConsole::update),
    DELETE("delete a customer", CustomerConsole::delete),
    LIST("list all customers", CustomerConsole::findAll),
    BLACK("list black customers", CustomerConsole::findAllBlackList);


    private final String document;
    private final Consumer<CustomerConsole> consumer;

    CustomerCommand(String document, Consumer<CustomerConsole> consumer) {
        this.document = document;
        this.consumer = consumer;
    }

    @Override
    public String getDocument() {
        return document;
    }

    @Override
    public void run(ConsoleIOManager consoleIOManager) {
        this.consumer.accept((CustomerConsole) consoleIOManager);
    }

    public Command[] test() {
        return CustomerCommand.values();
    }
}
