package com.wonu606.vouchermanager.controller.customer.converter;

import com.wonu606.vouchermanager.controller.customer.request.WalletDeleteRequest;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletDeleteParam;
import com.wonu606.vouchermanager.util.TypedConverter;
import java.util.UUID;
import org.springframework.core.convert.converter.Converter;

public class WalletDeleteParamConverter implements
        TypedConverter<WalletDeleteRequest, WalletDeleteParam> {

    @Override
    public WalletDeleteParam convert(WalletDeleteRequest request) {
        return new WalletDeleteParam(UUID.fromString(request.getVoucherId()),
                request.getCustomerId());
    }

    @Override
    public Class<WalletDeleteRequest> getSourceType() {
        return WalletDeleteRequest.class;
    }

    @Override
    public Class<WalletDeleteParam> getTargetType() {
        return WalletDeleteParam.class;
    }
}
