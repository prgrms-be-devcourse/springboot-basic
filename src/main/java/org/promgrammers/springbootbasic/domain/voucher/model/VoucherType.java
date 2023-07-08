package org.promgrammers.springbootbasic.domain.voucher.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.promgrammers.springbootbasic.global.error.exception.BusinessException;

import java.util.Arrays;

import static org.promgrammers.springbootbasic.global.error.exception.ErrorCode.INVALID_VOUCHER_TYPE;

public enum VoucherType {

    FIXED("1", "fixed"),
    PERCENT("2", "percent");

    private final String typeNumber;
    private final String typeName;

    VoucherType(String typeNumber, String typeName) {
        this.typeNumber = typeNumber;
        this.typeName = typeName;
    }

    public static VoucherType fromTypeString(String voucherType) {
        return Arrays.stream(values())
                .filter(voucher -> voucher.typeNumber.equals(voucherType) || voucher.typeName.equals(voucherType.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new BusinessException(INVALID_VOUCHER_TYPE));
    }

    @JsonCreator
    public static VoucherType from(String value) {
        return Arrays.stream(VoucherType.values())
                .filter(status -> status.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new BusinessException(INVALID_VOUCHER_TYPE));
    }

    @JsonValue
    public String getValue() {
        return typeName;
    }
}