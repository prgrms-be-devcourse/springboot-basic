package com.programmers.vouchermanagement.voucher.mapper;

import com.programmers.vouchermanagement.voucher.domain.*;

import java.util.UUID;

public class VoucherMapper {

    public static String fromEntity(Voucher voucher) {
        return voucher.getVoucherId() + "," + voucher.getVoucherType() + "," + voucher.getVoucherPolicy().getDiscount();
    }

    public static Voucher toEntity(String[] voucherData) {

        UUID voucherId = UUID.fromString(voucherData[0]);
        VoucherType voucherType = VoucherType.getVoucherTypeByName(voucherData[1]);
        Long discount = Long.parseLong(voucherData[2]);
        VoucherPolicy voucherPolicy = VoucherPolicyMapper.toEntity(discount, voucherType);

        return new Voucher(voucherId, voucherType, voucherPolicy);
    }
}
