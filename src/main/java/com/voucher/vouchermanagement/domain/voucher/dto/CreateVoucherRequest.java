package com.voucher.vouchermanagement.domain.voucher.dto;

import com.voucher.vouchermanagement.domain.voucher.model.Voucher;
import com.voucher.vouchermanagement.domain.voucher.model.VoucherType;

public class CreateVoucherRequest {
    private VoucherType type;
    private Long value;

    public CreateVoucherRequest(String type, Long value) {
        this.type = VoucherType.fromName(type);
        this.value = value;
    }

    protected CreateVoucherRequest() {
    }

    public VoucherType getType() {
        return type;
    }

    public Long getValue() {
        return value;
    }

    public static CreateVoucherRequest of(Voucher voucher) {
        return new CreateVoucherRequest(
                VoucherType.getVoucherTypeByName(voucher.getClass().getSimpleName()).name(),
                voucher.getValue()
        );
    }
}
