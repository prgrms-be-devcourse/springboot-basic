package com.wonu606.vouchermanager.service.voucherwallet.converter;

import com.wonu606.vouchermanager.service.voucherwallet.param.WalletDeleteParam;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletDeleteQuery;
import com.wonu606.vouchermanager.util.TypedConverter;

public class WalletDeleteQueryConverter implements
        TypedConverter<WalletDeleteParam, WalletDeleteQuery> {

    @Override
    public WalletDeleteQuery convert(WalletDeleteParam param) {
        return new WalletDeleteQuery(param.getCustomerEmail(), param.getVoucherUuid().toString());
    }

    @Override
    public Class<WalletDeleteParam> getSourceType() {
        return WalletDeleteParam.class;
    }

    @Override
    public Class<WalletDeleteQuery> getTargetType() {
        return WalletDeleteQuery.class;
    }
}
