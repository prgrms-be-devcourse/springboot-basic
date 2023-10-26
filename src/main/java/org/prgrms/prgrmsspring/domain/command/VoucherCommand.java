package org.prgrms.prgrmsspring.domain.command;

import org.prgrms.prgrmsspring.controller.ApplicationController;
import org.prgrms.prgrmsspring.controller.VoucherController;

import java.util.function.Consumer;

public enum VoucherCommand implements Command {
    CREATE("create a new voucher.", VoucherController::create),
    UPDATE("update a voucher.", VoucherController::update),
    DELETE("delete a voucher.", VoucherController::delete),
    LIST("list vouchers.", VoucherController::list);

    private final String document;
    private final Consumer<VoucherController> consumer;


    VoucherCommand(String document, Consumer<VoucherController> consumer) {
        this.document = document;
        this.consumer = consumer;
    }

    public static VoucherCommand from(String commandName) {
        return valueOf(commandName.toUpperCase());
    }

    @Override
    public String getDocument() {
        return document;
    }

    @Override
    public void run(ApplicationController controller) {
        this.consumer.accept((VoucherController) controller);
    }
}
