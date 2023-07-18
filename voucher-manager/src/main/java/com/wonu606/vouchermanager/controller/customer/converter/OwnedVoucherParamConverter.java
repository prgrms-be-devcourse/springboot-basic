package com.wonu606.vouchermanager.controller.customer.converter;

import com.wonu606.vouchermanager.controller.customer.request.OwnedVouchersRequest;
import com.wonu606.vouchermanager.service.customer.param.OwnedVoucherParam;

public class OwnedVoucherParamConverter {

    public OwnedVoucherParam convert(OwnedVouchersRequest request) {
        return new OwnedVoucherParam(request.getCustomerId());
    }
}
