package com.wonu606.vouchermanager.controller.voucherwallet.converter;

import com.wonu606.vouchermanager.controller.voucherwallet.response.VoucherResponse;
import com.wonu606.vouchermanager.service.voucher.result.VoucherResult;
import com.wonu606.vouchermanager.util.TypedConverter;

public class VoucherResponseConverter implements TypedConverter<VoucherResult, VoucherResponse> {

    @Override
    public VoucherResponse convert(VoucherResult result) {
        return new VoucherResponse(result.getUuid(), result.getVoucherClassType(),
                result.getDiscountValue());
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
