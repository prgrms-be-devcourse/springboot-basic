package com.wonu606.vouchermanager.controller.voucher.converter;

import com.wonu606.vouchermanager.controller.voucher.reqeust.OwnedCustomersRequest;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedCustomersParam;
import java.util.UUID;
import org.springframework.core.convert.converter.Converter;

public class OwnedCustomersParamConverter implements Converter<OwnedCustomersRequest, OwnedCustomersParam> {

    @Override
    public OwnedCustomersParam convert(OwnedCustomersRequest request) {
        return new OwnedCustomersParam(UUID.fromString(request.getVoucherId()));
    }
}
