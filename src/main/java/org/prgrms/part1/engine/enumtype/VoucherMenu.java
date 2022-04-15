package org.prgrms.part1.engine.enumtype;

import org.prgrms.part1.engine.VoucherFunction;

import java.util.Arrays;
import java.util.Optional;

public enum VoucherMenu {
    EXIT("exit") {
        @Override
        public Boolean runMethod(VoucherFunction voucherFunction) {
            return true;
        }
    },
    CREATE("create") {
        @Override
        public Boolean runMethod(VoucherFunction voucherFunction) {
            voucherFunction.showCreateMenu();
            return false;
        }
    },
    LIST("list") {
        @Override
        public Boolean runMethod(VoucherFunction voucherFunction) {
            voucherFunction.showListMenu();
            return false;
        }
    };

    private final String string;


    VoucherMenu(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public abstract Boolean runMethod(VoucherFunction voucherFunction);

    public static Optional<VoucherMenu> findMatchingMenu(String input) {
        return Optional.ofNullable(Arrays.stream(values()).filter(vm -> vm.string.equals(input)).findFirst().orElse(null));
    }
}
