package org.programmers.springbootbasic.domain.voucher.dto;

import org.programmers.springbootbasic.data.VoucherType;
import org.programmers.springbootbasic.domain.voucher.model.FixedAmountVoucher;
import org.programmers.springbootbasic.domain.voucher.model.PercentDiscountVoucher;
import org.programmers.springbootbasic.domain.voucher.model.Voucher;

import java.util.UUID;

public class VoucherDBOutputDto {
    UUID voucherId;
    String type;
    VoucherType voucherType;
    long amount;



    public VoucherDBOutputDto(UUID voucherId, String type, long amount) {
        this.voucherType = convertToVoucherType(type);
        this.voucherId = voucherId;
        this.amount = amount;
    }

    private VoucherType convertToVoucherType(String type) {
        // valid 하다면 VoucherType 으로 convert.
        return VoucherType.valueOfType(type);
    }

    public Voucher toVoucher() {
        if(voucherType == VoucherType.FIXED) {
            return new FixedAmountVoucher(voucherId, amount);
        }
        else {
            return new PercentDiscountVoucher(voucherId, amount);
        }
    }

}
