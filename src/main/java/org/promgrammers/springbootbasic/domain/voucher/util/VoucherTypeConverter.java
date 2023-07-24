package org.promgrammers.springbootbasic.domain.voucher.util;

import org.promgrammers.springbootbasic.domain.voucher.model.VoucherType;
import org.springframework.core.convert.converter.Converter;

public class VoucherTypeConverter implements Converter<String, VoucherType> {

    @Override
    public VoucherType convert(String voucherType) {
        return VoucherType.fromTypeString(voucherType);
    }
}
