package com.wonu606.vouchermanager.controller.voucher.converter;

import com.wonu606.vouchermanager.controller.voucher.response.VoucherResponse;
import com.wonu606.vouchermanager.service.voucher.result.VoucherResult;
import org.springframework.core.convert.converter.Converter;

public class VoucherResponseConverter implements Converter<VoucherResult, VoucherResponse> {

    @Override
    public VoucherResponse convert(VoucherResult result) {
        return new VoucherResponse(result.getVoucherUuid());
    }
}
