package com.wonu606.vouchermanager.controller.voucher.converter;

import com.wonu606.vouchermanager.controller.voucher.response.OwnedCustomerResponse;
import com.wonu606.vouchermanager.service.voucherwallet.result.OwnedCustomerResult;
import org.springframework.core.convert.converter.Converter;

public class OwnedCustomerResponseConverter implements Converter<OwnedCustomerResult, OwnedCustomerResponse> {

    @Override
    public OwnedCustomerResponse convert(OwnedCustomerResult result) {
        return new OwnedCustomerResponse(result.getCustomerEmail());
    }
}
