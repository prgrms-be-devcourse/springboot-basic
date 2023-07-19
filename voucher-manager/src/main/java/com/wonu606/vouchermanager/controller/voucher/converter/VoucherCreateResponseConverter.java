package com.wonu606.vouchermanager.controller.voucher.converter;

import com.wonu606.vouchermanager.controller.voucher.response.VoucherCreateResponse;
import com.wonu606.vouchermanager.service.voucher.result.VoucherCreateResult;
import com.wonu606.vouchermanager.util.TypedConverter;
import org.springframework.core.convert.converter.Converter;

public class VoucherCreateResponseConverter implements
        TypedConverter<VoucherCreateResult, VoucherCreateResponse> {

    @Override
    public VoucherCreateResponse convert(VoucherCreateResult result) {
        return new VoucherCreateResponse(result.getTaskSuccess());
    }

    @Override
    public Class<VoucherCreateResult> getSourceType() {
        return VoucherCreateResult.class;
    }

    @Override
    public Class<VoucherCreateResponse> getTargetType() {
        return VoucherCreateResponse.class;
    }
}
