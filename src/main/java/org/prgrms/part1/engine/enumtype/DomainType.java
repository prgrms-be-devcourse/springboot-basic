package org.prgrms.part1.engine.enumtype;

import org.prgrms.part1.engine.VoucherFunction;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum DomainType {
    VOUCHER("1") {
        @Override
        public void showList(VoucherFunction voucherFunction) {
            voucherFunction.showVouchers();
        }
        @Override
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
        @Override
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

    public String getCode() {
        return code;
    }

    public abstract void showList(VoucherFunction voucherFunction);

    public abstract void showCreate(VoucherFunction voucherFunction);

    public abstract void showSearch(VoucherFunction voucherFunction);

    private static final Map<String, DomainType> map = Collections.unmodifiableMap(Stream.of(values()).collect(Collectors.toMap(DomainType::getCode, Function.identity())));

    public static Optional<DomainType> findMatchingCode(String input) {
        return Optional.ofNullable(map.get(input));
    }
}
