package com.mountain.voucherApp.shared.enums;

import com.mountain.voucherApp.adapter.in.console.VoucherConsoleController;
import com.mountain.voucherApp.shared.constants.ProgramMessage;
import java.util.function.Consumer;

import static com.mountain.voucherApp.shared.constants.ProgramMessage.*;

public enum Menu {

    EXIT(ProgramMessage.EXIT, EXIT_PROGRAM_DESC, voucherController -> voucherController.exit()),
    CREATE(ProgramMessage.CREATE, CREATE_VOUCHER_DESC, voucherController -> voucherController.create()),
    LIST(ProgramMessage.LIST, LIST_VOUCHERS_DESC, voucherController -> voucherController.showVoucherList()),
    ADD_VOUCHER(ProgramMessage.ADD_VOUCHER, ADD_VOUCHER_DESC, voucherController -> voucherController.addVoucher()),
    CUSTOMER_LIST(ProgramMessage.CUSTOMER_LIST, CUSTOMER_LIST_DESC, voucherController -> voucherController.showCustomerVoucherInfo()),
    REMOVE_VOUCHER(ProgramMessage.REMOVE_VOUCHER, REMOVE_VOUCHER_DESC, voucherController -> voucherController.removeVoucher()),
    LIST_BY_VOUCHER(ProgramMessage.LIST_BY_VOUCHER, LIST_BY_VOUCHER_DESC, voucherController -> voucherController.showByVoucher());

    private final String value;
    private final String description;
    private final Consumer<VoucherConsoleController> consumer;

    Menu(String value, String description, Consumer<VoucherConsoleController> consumer) {
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

    public void exec(VoucherConsoleController voucherConsoleController) {
        consumer.accept(voucherConsoleController);
    }
}
