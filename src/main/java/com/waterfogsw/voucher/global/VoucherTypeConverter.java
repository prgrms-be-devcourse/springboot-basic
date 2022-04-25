package com.waterfogsw.voucher.global;

import com.waterfogsw.voucher.voucher.domain.VoucherType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VoucherTypeConverter implements Converter<String, VoucherType> {
    @Override
    public VoucherType convert(String source) {
        return VoucherType.valueOf(source.toUpperCase());
    }
}
