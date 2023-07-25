package com.wonu606.vouchermanager.controller.customer.converter;

import com.wonu606.vouchermanager.controller.customer.response.OwnedVoucherResponse;
import com.wonu606.vouchermanager.service.voucherwallet.result.OwnedVoucherResult;
import com.wonu606.vouchermanager.util.TypedConverter;

public class OwnedVoucherResponseConverter implements
        TypedConverter<OwnedVoucherResult, OwnedVoucherResponse> {

    @Override
    public OwnedVoucherResponse convert(OwnedVoucherResult result) {
        return new OwnedVoucherResponse(result.getVoucherUuid());
    }

    @Override
    public Class<OwnedVoucherResult> getSourceType() {
        return OwnedVoucherResult.class;
    }

    @Override
    public Class<OwnedVoucherResponse> getTargetType() {
        return OwnedVoucherResponse.class;
    }
}
