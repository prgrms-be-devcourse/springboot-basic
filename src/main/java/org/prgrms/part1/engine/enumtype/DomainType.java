package org.prgrms.part1.engine.enumtype;

import org.prgrms.part1.engine.VoucherFunction;

import java.util.Arrays;
import java.util.Optional;

public enum DomainType {
    VOUCHER("1") {
        @Override
        public void showList(VoucherFunction voucherFunction) {
            voucherFunction.showVouchers();
        }
        public void showCreate(VoucherFunction voucherFunction) {
            voucherFunction.showVoucherCreateMenu();
        }

        @Override
        public void showSearch(VoucherFunction voucherFunction) {
            voucherFunction.searchVoucher();
        }
    }, CUSTOMER("2") {
        @Override
        public void showList(VoucherFunction voucherFunction) {
            voucherFunction.showCustomers();
        }
        public void showCreate(VoucherFunction voucherFunction) {
            voucherFunction.createCustomer();
        }

        @Override
        public void showSearch(VoucherFunction voucherFunction) {
            voucherFunction.searchCustomer();
        }
    };

    private final String code;

    DomainType(String code) {
        this.code = code;
    }

    public abstract void showList(VoucherFunction voucherFunction);

    public abstract void showCreate(VoucherFunction voucherFunction);

    public abstract void showSearch(VoucherFunction voucherFunction);

    public static Optional<DomainType> findMatchingCode(String input) {
        return Optional.ofNullable(Arrays.stream(values()).filter(lt -> lt.code.equals(input)).findFirst().orElse(null));
    }
}
