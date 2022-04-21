package org.prgrms.springbootbasic.config;

import org.prgrms.springbootbasic.controller.VoucherType;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, VoucherType> {

    @Override
    public VoucherType convert(String source) {
        return VoucherType.valueOf(source.toUpperCase());
    }
}
