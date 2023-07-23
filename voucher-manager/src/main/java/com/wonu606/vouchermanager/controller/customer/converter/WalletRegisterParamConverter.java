package com.wonu606.vouchermanager.controller.customer.converter;

import com.wonu606.vouchermanager.controller.customer.request.WalletRegisterRequest;
import com.wonu606.vouchermanager.service.customer.param.WalletRegisterParam;
import com.wonu606.vouchermanager.util.TypedConverter;
import java.util.UUID;

public class WalletRegisterParamConverter implements
        TypedConverter<WalletRegisterRequest, WalletRegisterParam> {

    @Override
    public Class<WalletRegisterRequest> getSourceType() {
        return WalletRegisterRequest.class;
    }

    @Override
    public Class<WalletRegisterParam> getTargetType() {
        return WalletRegisterParam.class;
    }

    @Override
    public WalletRegisterParam convert(WalletRegisterRequest request) {
        return new WalletRegisterParam(
                UUID.fromString(request.getVoucherId()),
                request.getCustomerId());
    }
}
