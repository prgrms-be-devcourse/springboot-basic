package org.promgrammers.springbootbasic.domain.voucher.model;

import com.fasterxml.jackson.annotation.JsonCreator;
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

    @JsonCreator
    public static VoucherType fromTypeString(String voucherType) {
        return Arrays.stream(values())
                .filter(voucher -> voucher.typeNumber.equals(voucherType) || voucher.typeName.equalsIgnoreCase(voucherType))
                .findFirst()
                .orElseThrow(() -> new BusinessException(INVALID_VOUCHER_TYPE));
    }
}