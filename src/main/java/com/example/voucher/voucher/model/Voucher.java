package com.example.voucher.voucher.model;

import static com.example.voucher.constant.ExceptionMessage.*;
import java.util.UUID;
import com.example.voucher.constant.VoucherType;
import com.example.voucher.query.marker.Entity;

public interface Voucher extends Entity {

    UUID getVoucherId();

    Long getValue();

    VoucherType getVoucherType();

    long discount(long originalAmount);

    default void validatePositive(long value) {
        if (value <= 0) {
            throw new IllegalArgumentException(MESSAGE_ERROR_POSITIVE_CONSTRAINT);
        }
    }

}
