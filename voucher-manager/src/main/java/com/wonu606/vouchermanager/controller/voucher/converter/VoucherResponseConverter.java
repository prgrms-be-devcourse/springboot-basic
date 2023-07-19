package com.wonu606.vouchermanager.controller.voucher.converter;

import com.wonu606.vouchermanager.controller.voucher.response.VoucherResponse;
import com.wonu606.vouchermanager.service.voucher.result.VoucherResult;
import com.wonu606.vouchermanager.util.TypedConverter;
import org.springframework.core.convert.converter.Converter;

public class VoucherResponseConverter implements TypedConverter<VoucherResult, VoucherResponse> {

    @Override
    public VoucherResponse convert(VoucherResult result) {
        return new VoucherResponse(result.getVoucherUuid());
    }

    @Override
    public Class<VoucherResult> getSourceType() {
        return VoucherResult.class;
    }

    @Override
    public Class<VoucherResponse> getTargetType() {
        return VoucherResponse.class;
    }
}
