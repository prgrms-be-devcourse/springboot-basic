package com.prgrms.voucher.service.dto;

import com.prgrms.voucher.model.Voucher;
import com.prgrms.voucher.model.VoucherType;
import java.util.Objects;;

public record VoucherServiceResponse(VoucherType voucherType,
                                     double discount,
                                     int voucherId) {

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
        return Double.compare(that.discount, discount) == 0 && voucherId == that.voucherId
                && voucherType == that.voucherType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherType, discount, voucherId);
    }

}
