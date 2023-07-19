package com.wonu606.vouchermanager.controller.customer.converter;

import com.wonu606.vouchermanager.controller.customer.request.WalletDeleteRequest;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletDeleteParam;
import java.util.UUID;
import org.springframework.core.convert.converter.Converter;

public class WalletDeleteParamConverter implements
        Converter<WalletDeleteRequest, WalletDeleteParam> {

    @Override
    public WalletDeleteParam convert(WalletDeleteRequest request) {
        return new WalletDeleteParam(UUID.fromString(request.getVoucherId()),
                request.getCustomerId());
    }
}
