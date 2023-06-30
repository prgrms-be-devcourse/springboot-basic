package com.devcourse.springbootbasic.application.util.converter;

import com.devcourse.springbootbasic.application.domain.Voucher;
import com.devcourse.springbootbasic.application.dto.VoucherDto;
import com.devcourse.springbootbasic.application.dto.VoucherType;
import com.devcourse.springbootbasic.application.util.factory.FixedVoucherFactory;
import com.devcourse.springbootbasic.application.util.factory.PercentVoucherFactory;

public class DtoConverter {

    public static Voucher convertDtoToDomain(VoucherDto voucherDto) {
        if (voucherDto.voucherType().equals(VoucherType.FIXED_AMOUNT)) {
            return new FixedVoucherFactory().create(voucherDto.discountValue());
        }
        return new PercentVoucherFactory().create(voucherDto.discountValue());
    }

}
