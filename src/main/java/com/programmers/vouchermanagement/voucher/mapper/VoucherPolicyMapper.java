package com.programmers.vouchermanagement.voucher.mapper;

import com.programmers.vouchermanagement.voucher.domain.FixedVoucherPolicy;
import com.programmers.vouchermanagement.voucher.domain.PercentVoucherPolicy;
import com.programmers.vouchermanagement.voucher.domain.VoucherPolicy;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;

public class VoucherPolicyMapper {

    public static VoucherPolicy toEntity(Long discount, VoucherType voucherType) {

        VoucherPolicy voucherPolicy = null;

        switch (voucherType) {

            case FIXED -> voucherPolicy = new FixedVoucherPolicy(discount);

            case PERCENT -> voucherPolicy = new PercentVoucherPolicy(discount);
        }

        return voucherPolicy;
    }
}
