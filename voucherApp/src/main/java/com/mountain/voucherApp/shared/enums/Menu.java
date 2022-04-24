package com.mountain.voucherApp.shared.enums;

import com.mountain.voucherApp.adapter.in.console.VoucherAppController;
import com.mountain.voucherApp.shared.constants.ProgramMessage;
import java.util.function.Consumer;

import static com.mountain.voucherApp.shared.constants.ProgramMessage.*;

public enum Menu {

    EXIT(ProgramMessage.EXIT, EXIT_PROGRAM_DESC, menuStrategy -> menuStrategy.exit()),
    CREATE(ProgramMessage.CREATE, CREATE_VOUCHER_DESC, menuStrategy -> menuStrategy.create()),
    LIST(ProgramMessage.LIST, LIST_VOUCHERS_DESC, menuStrategy -> menuStrategy.showVoucherList()),
    ADD_VOUCHER(ProgramMessage.ADD_VOUCHER, ADD_VOUCHER_DESC, menuStrategy -> menuStrategy.addVoucher()),
    CUSTOMER_LIST(ProgramMessage.CUSTOMER_LIST, CUSTOMER_LIST_DESC, menuStrategy -> menuStrategy.showCustomerVoucherInfo()),
    REMOVE_VOUCHER(ProgramMessage.REMOVE_VOUCHER, REMOVE_VOUCHER_DESC, menuStrategy -> menuStrategy.removeVoucher()),
    LIST_BY_VOUCHER(ProgramMessage.LIST_BY_VOUCHER, LIST_BY_VOUCHER_DESC, menuStrategy -> menuStrategy.showByVoucher());

    private final String value;
    private final String description;
    private final Consumer<VoucherAppController> consumer;

    Menu(String value, String description, Consumer<VoucherAppController> consumer) {
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

    public void exec(VoucherAppController voucherAppController) {
        consumer.accept(voucherAppController);
    }
}
