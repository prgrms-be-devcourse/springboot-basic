package com.wonu606.vouchermanager.controller.voucher.converter;

import com.wonu606.vouchermanager.controller.voucher.response.OwnedCustomerResponse;
import com.wonu606.vouchermanager.service.voucherwallet.result.OwnedCustomerResult;
import com.wonu606.vouchermanager.util.TypedConverter;

public class OwnedCustomerResponseConverter implements
        TypedConverter<OwnedCustomerResult, OwnedCustomerResponse> {

    @Override
    public OwnedCustomerResponse convert(OwnedCustomerResult result) {
        return new OwnedCustomerResponse(result.getCustomerEmail());
    }

    @Override
    public Class<OwnedCustomerResult> getSourceType() {
        return OwnedCustomerResult.class;
    }

    @Override
    public Class<OwnedCustomerResponse> getTargetType() {
        return OwnedCustomerResponse.class;
    }
}
