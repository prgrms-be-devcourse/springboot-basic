package com.prgrms.voucher.service.dto;

import com.prgrms.voucher.model.voucher.Voucher;
import com.prgrms.voucher.model.VoucherType;
import java.util.Objects;

public record VoucherServiceResponse(VoucherType voucherType,
                                     double discount,
                                     String voucherId) {

    public VoucherServiceResponse(Voucher voucher) {
        this(voucher.getVoucherType(), voucher.getVoucherDiscount().getDiscountAmount(), voucher.getVoucherId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VoucherServiceResponse that = (VoucherServiceResponse) o;
        return Double.compare(that.discount, discount) == 0 && voucherType == that.voucherType
                && Objects.equals(voucherId, that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherType, discount, voucherId);
    }

}
