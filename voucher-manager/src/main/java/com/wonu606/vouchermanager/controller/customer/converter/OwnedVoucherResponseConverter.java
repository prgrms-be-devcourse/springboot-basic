package com.wonu606.vouchermanager.controller.customer.converter;

import com.wonu606.vouchermanager.controller.customer.response.OwnedVoucherResponse;
import com.wonu606.vouchermanager.service.voucherwallet.result.OwnedVoucherResult;
import org.springframework.core.convert.converter.Converter;

public class OwnedVoucherResponseConverter implements Converter<OwnedVoucherResult, OwnedVoucherResponse> {

    @Override
    public OwnedVoucherResponse convert(OwnedVoucherResult result) {
        return new OwnedVoucherResponse(result.getVoucherUuid());
    }
}
