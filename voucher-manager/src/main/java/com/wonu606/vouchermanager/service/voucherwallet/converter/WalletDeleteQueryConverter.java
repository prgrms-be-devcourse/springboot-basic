package com.wonu606.vouchermanager.service.voucherwallet.converter;

import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletDeleteQuery;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletDeleteParam;
import org.springframework.core.convert.converter.Converter;

public class WalletDeleteQueryConverter implements Converter<WalletDeleteParam, WalletDeleteQuery> {

    @Override
    public WalletDeleteQuery convert(WalletDeleteParam param) {
        return new WalletDeleteQuery(param.getCustomerEmail(), param.getVoucherUuid().toString());
    }
}
