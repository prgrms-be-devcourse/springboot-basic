package org.prgrms.prgrmsspring.domain;

import lombok.Getter;

public enum FunctionType {
    EXIT(0),
    CUSTOMER(1),
    VOUCHER(2),
    WALLET(3);

    @Getter
    private final int modeNumber;

    FunctionType(int modeNumber) {
        this.modeNumber = modeNumber;
    }
}
