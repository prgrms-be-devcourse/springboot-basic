package org.prgrms.vouchermanager.domain.voucher;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.vouchermanager.exception.InputValueException;

@Slf4j
public enum VoucherType {
    CREATE("create"),
    LIST("list"),
    EXIT("exit"),
    FIXED("fixed"),
    PERCENT("percent");
    private final String value;

    VoucherType(String value) {
        this.value = value;
    }


    public static VoucherType fromValue(String value) {
        for (VoucherType menu : VoucherType.values()) {
            if (menu.value.equalsIgnoreCase(value)) {
                return menu;
            }
        }
        log.error("value : {}", value);
        throw new InputValueException();
    }
}
