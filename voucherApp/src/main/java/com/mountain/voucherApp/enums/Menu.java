package com.mountain.voucherApp.enums;

import com.mountain.voucherApp.constants.Message;
import com.mountain.voucherApp.engine.Strategy;

import java.util.function.Consumer;

import static com.mountain.voucherApp.constants.Message.*;

public enum Menu {

    EXIT(Message.EXIT, EXIT_PROGRAM_DESC, strategy -> strategy.exit()),
    CREATE(Message.CREATE, CREATE_VOUCHER_DESC, strategy -> strategy.create()),
    LIST(Message.LIST, LIST_VOUCHERS_DESC, strategy -> strategy.showVoucherList()),
    ADD_VOUCHER(Message.ADD_VOUCHER, ADD_VOUCHER_DESC, strategy -> strategy.addVoucher()),
    CUSTOMER_LIST(Message.CUSTOMER_LIST, CUSTOMER_LIST_DESC, strategy -> strategy.showCustomerList()),
    REMOVE_VOUCHER(Message.REMOVE_VOUCHER, REMOVE_VOUCHER_DESC, strategy -> strategy.removeVoucher()),
    LIST_BY_VOUCHER(Message.LIST_BY_VOUCHER, LIST_BY_VOUCHER_DESC, strategy -> strategy.showByVoucher());

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
