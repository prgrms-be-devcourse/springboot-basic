package com.voucher.vouchermanagement.dto.voucher;

import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.model.voucher.VoucherType;

public class CreateVoucherRequest {
    private VoucherType type;
    private Long value;

    public CreateVoucherRequest(VoucherType type, Long value) {
        this.type = type;
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
                VoucherType.getVoucherTypeByName(voucher.getClass().getSimpleName()),
                voucher.getValue()
        );
    }
}
