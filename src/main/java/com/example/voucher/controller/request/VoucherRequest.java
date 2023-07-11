package com.example.voucher.controller.request;

import static com.example.voucher.constant.ExceptionMessage.*;
import java.util.Arrays;
import java.util.UUID;
import com.example.voucher.domain.Voucher;

public class VoucherRequest {

    public enum Type {

        CREATE,
        LIST,
        REMOVE,
        SEARCH_BY_ID;

        public static Type getType(String inputTypeName) {
            return Arrays.stream(values())
                .filter(m -> m.name().equalsIgnoreCase(inputTypeName))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_ARGUMENT_RETRY_MODE_TYPE_SELECTION));
        }

    }

    private final Type type;

    private UUID voucherId;

    private Voucher.Type voucherType;

    private Long discountValue;

    public VoucherRequest(VoucherRequest.Type type) {
        this.type = type;
        this.voucherType = null;
        this.discountValue = null;
    }

    public VoucherRequest.Type getType() {
        return type;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public Voucher.Type getVoucherType() {
        return voucherType;
    }

    public Long getDiscountValue() {
        return discountValue;
    }

    public void setVoucherId(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public void setVoucherCreateInfo(Voucher.Type voucherType, Long discountValue) {
        this.voucherType = voucherType;
        this.discountValue = discountValue;
    }

}
