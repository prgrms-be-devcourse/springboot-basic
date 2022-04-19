package com.mountain.voucherApp.enums;

import com.mountain.voucherApp.constants.Message;
import com.mountain.voucherApp.engine.Strategy;

import java.util.function.Consumer;

import static com.mountain.voucherApp.constants.Message.*;

public enum Menu {

    EXIT(Message.EXIT, EXIT_PROGRAM, strategy -> strategy.exit()),
    CREATE(Message.CREATE, CREATE_VOUCHER, strategy -> strategy.create()),
    LIST(Message.LIST, LIST_VOUCHERS, strategy -> strategy.showVoucherList());

    private final String value;
    private final String description;
    private final Consumer<Strategy> consumer;

    Menu(String value, String description, Consumer<Strategy> consumer) {
        this.value = value;
        this.description = description;
        this.consumer = consumer;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public void exec(Strategy strategy) {
        consumer.accept(strategy);
    }
}
