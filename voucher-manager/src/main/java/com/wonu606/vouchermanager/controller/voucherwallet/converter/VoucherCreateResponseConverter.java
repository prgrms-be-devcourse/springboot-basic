package com.wonu606.vouchermanager.controller.voucherwallet.converter;

import com.wonu606.vouchermanager.controller.voucherwallet.response.VoucherCreateResponse;
import com.wonu606.vouchermanager.service.voucher.result.VoucherCreateResult;
import com.wonu606.vouchermanager.util.TypedConverter;

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
