package com.programmers.vouchermanagement.voucher.mapper;

import com.programmers.vouchermanagement.voucher.domain.FixedAmountVoucher;
import com.programmers.vouchermanagement.voucher.domain.PercentDiscountVoucher;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;

import java.util.UUID;

public class VoucherMapper {

    public static String fromEntity(Voucher voucher) {
        return voucher.getVoucherId() + "," + voucher.getVoucherType() + "," + voucher.getDiscount();
    }

    public static Voucher toEntity(String[] voucherData) {

        UUID voucherId = UUID.fromString(voucherData[0]);
        VoucherType voucherType = VoucherType.getVoucherTypeByName(voucherData[1].toLowerCase());
        Long discount = Long.parseLong(voucherData[2]);

        Voucher voucher = null;

        switch (voucherType) {

            case FIXED -> voucher = new FixedAmountVoucher(voucherId, discount, voucherType);

            case PERCENT -> voucher = new PercentDiscountVoucher(voucherId, discount, voucherType);
        }

        return voucher;
    }
}
