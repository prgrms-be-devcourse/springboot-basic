package com.programmers.voucher.request;

import com.programmers.voucher.exception.VoucherErrorCode;
import com.programmers.voucher.exception.VoucherException;

import java.util.Objects;

public class VoucherCreationRequest {
    private String type;
    private long amount;

    public VoucherCreationRequest(String type, long amount) {
        validateVoucherCreationRequest(type, amount);
        this.type = type;
        this.amount = amount;
    }

    private static void validateVoucherCreationRequest(String type, long amount) {
        if(Objects.isNull(type) || amount < 0) {
            throw new VoucherException(VoucherErrorCode.INVALID_VOUCHER_CREATION_REQUEST);
        }
    }

    public String getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }
}
