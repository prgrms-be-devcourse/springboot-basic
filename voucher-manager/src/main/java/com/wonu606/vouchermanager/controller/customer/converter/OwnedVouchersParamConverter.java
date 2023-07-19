package com.wonu606.vouchermanager.controller.customer.converter;

import com.wonu606.vouchermanager.controller.customer.request.OwnedVouchersRequest;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedVouchersParam;

public class OwnedVouchersParamConverter {

    public OwnedVouchersParam convert(OwnedVouchersRequest request) {
        return new OwnedVouchersParam(request.getCustomerId());
    }
}
