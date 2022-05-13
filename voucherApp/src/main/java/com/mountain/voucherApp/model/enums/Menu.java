package com.mountain.voucherApp.model.enums;

import com.mountain.voucherApp.common.constants.ProgramMessage;
import com.mountain.voucherApp.controller.console.VoucherConsoleController;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mountain.voucherApp.common.constants.ProgramMessage.*;

public enum Menu {

    EXIT(1, ProgramMessage.EXIT, EXIT_PROGRAM_DESC, VoucherConsoleController::exit),
    CREATE(2, ProgramMessage.CREATE, CREATE_VOUCHER_DESC, VoucherConsoleController::create),
    LIST(3, ProgramMessage.LIST, LIST_VOUCHERS_DESC, VoucherConsoleController::showVoucherList),
    ADD_VOUCHER(4, ProgramMessage.ADD_VOUCHER, ADD_VOUCHER_DESC, VoucherConsoleController::addVoucher),
    CUSTOMER_LIST(5, ProgramMessage.CUSTOMER_LIST, CUSTOMER_LIST_DESC, VoucherConsoleController::showCustomerVoucherInfo),
    REMOVE_VOUCHER(6, ProgramMessage.REMOVE_VOUCHER, REMOVE_VOUCHER_DESC, VoucherConsoleController::removeVoucher),
    LIST_BY_VOUCHER(7, ProgramMessage.LIST_BY_VOUCHER, LIST_BY_VOUCHER_DESC, VoucherConsoleController::showByVoucher);

    public static final Map<Integer, Menu> menuMap =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toConcurrentMap(Menu::getSeq, Function.identity())));
    private final String value;
    private final String description;
    private final Consumer<VoucherConsoleController> consumer;
    private final int seq;

    Menu(int seq, String value, String description, Consumer<VoucherConsoleController> consumer) {
        this.seq = seq;
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

    public static Optional<Menu> find(int seq) {
        return Optional.ofNullable(menuMap.get(seq));
    }

    public static boolean isExit(int seq) {
        return menuMap.get(seq) == Menu.EXIT;
    }

    public int getSeq() {
        return seq;
    }
}
