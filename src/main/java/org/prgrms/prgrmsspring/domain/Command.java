package org.prgrms.prgrmsspring.domain;

import org.prgrms.prgrmsspring.controller.ApplicationController;

import java.util.function.Consumer;

public enum Command {
    EXIT("exit the program.", ApplicationController::exit),
    CREATE("create a new voucher.", ApplicationController::create),
    LIST("list all vouchers.", ApplicationController::list),
    BLACKLIST("list all blacklist people", ApplicationController::showBlackList);

    private final String document;
    private final Consumer<ApplicationController> consumer;

    Command(String document, Consumer<ApplicationController> consumer) {
        this.document = document;
        this.consumer = consumer;
    }

    public String getDocument() {
        return document;
    }

    public static Command of(String name) throws IllegalArgumentException {
        if (name.toLowerCase().equals(name)) {
            return valueOf(name.toUpperCase());
        }
        return valueOf(name);
    }

    public void run(ApplicationController controller) {
        this.consumer.accept(controller);
    }
}
