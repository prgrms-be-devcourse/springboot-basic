package com.dev.voucherproject.controller.web.converter;

import com.dev.voucherproject.model.voucher.VoucherPolicy;
import org.springframework.core.convert.converter.Converter;

public class VoucherPolicyConverter implements Converter<String, VoucherPolicy> {
    @Override
    public VoucherPolicy convert(String source) {
        return VoucherPolicy.valueOf(source);
    }
}
