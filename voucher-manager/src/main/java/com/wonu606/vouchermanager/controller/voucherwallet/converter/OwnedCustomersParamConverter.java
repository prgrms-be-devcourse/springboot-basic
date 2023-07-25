package com.wonu606.vouchermanager.controller.voucherwallet.converter;

import com.wonu606.vouchermanager.controller.voucherwallet.reqeust.OwnedCustomersRequest;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedCustomersParam;
import com.wonu606.vouchermanager.util.TypedConverter;
import java.util.UUID;

public class OwnedCustomersParamConverter implements
        TypedConverter<OwnedCustomersRequest, OwnedCustomersParam> {

    @Override
    public OwnedCustomersParam convert(OwnedCustomersRequest request) {
        return new OwnedCustomersParam(UUID.fromString(request.getVoucherId()));
    }

    @Override
    public Class<OwnedCustomersRequest> getSourceType() {
        return OwnedCustomersRequest.class;
    }

    @Override
    public Class<OwnedCustomersParam> getTargetType() {
        return OwnedCustomersParam.class;
    }
}
