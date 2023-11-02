package org.prgrms.prgrmsspring.domain.command;

import org.prgrms.prgrmsspring.controller.ApplicationController;
import org.prgrms.prgrmsspring.controller.CustomerController;

import java.util.function.Consumer;

public enum CustomerCommand implements Command {
    CREATE("create a new customer", CustomerController::create),
    UPDATE("update a customer", CustomerController::update),
    DELETE("delete a customer", CustomerController::delete),
    LIST("list all customers", CustomerController::findAll),
    BLACK("list black customers", CustomerController::findAllBlackList);


    private final String document;
    private final Consumer<CustomerController> consumer;

    CustomerCommand(String document, Consumer<CustomerController> consumer) {
        this.document = document;
        this.consumer = consumer;
    }

    @Override
    public String getDocument() {
        return document;
    }

    @Override
    public void run(ApplicationController controller) {
        this.consumer.accept((CustomerController) controller);
    }
}
