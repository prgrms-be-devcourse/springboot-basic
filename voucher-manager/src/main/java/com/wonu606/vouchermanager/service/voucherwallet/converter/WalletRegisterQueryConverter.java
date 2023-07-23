package com.wonu606.vouchermanager.service.voucherwallet.converter;

import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletRegisterQuery;
import com.wonu606.vouchermanager.service.customer.param.WalletRegisterParam;
import com.wonu606.vouchermanager.util.TypedConverter;

public class WalletRegisterQueryConverter implements TypedConverter<WalletRegisterParam, WalletRegisterQuery> {

    @Override
    public Class<WalletRegisterParam> getSourceType() {
        return WalletRegisterParam.class;
    }

    @Override
    public Class<WalletRegisterQuery> getTargetType() {
        return WalletRegisterQuery.class;
    }

    @Override
    public WalletRegisterQuery convert(WalletRegisterParam param) {
        return new WalletRegisterQuery(
                param.getCustomerId(),
                param.getVoucherId().toString());
    }
}
