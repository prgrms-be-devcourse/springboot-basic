package com.wonu606.vouchermanager.controller.customer.converter;

import com.wonu606.vouchermanager.controller.customer.request.OwnedVouchersRequest;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedVouchersParam;
import com.wonu606.vouchermanager.util.TypedConverter;
import org.springframework.core.convert.converter.Converter;

public class OwnedVouchersParamConverter implements
        TypedConverter<OwnedVouchersRequest, OwnedVouchersParam> {

    public OwnedVouchersParam convert(OwnedVouchersRequest request) {
        return new OwnedVouchersParam(request.getCustomerId());
    }

    @Override
    public Class<OwnedVouchersRequest> getSourceType() {
        return OwnedVouchersRequest.class;
    }

    @Override
    public Class<OwnedVouchersParam> getTargetType() {
        return OwnedVouchersParam.class;
    }
}
