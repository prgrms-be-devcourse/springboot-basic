package com.wonu606.vouchermanager.service.voucherwallet.converter;

import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletInsertQuery;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletAssignParam;
import org.springframework.core.convert.converter.Converter;

public class WalletInsertQueryConverter implements Converter<WalletAssignParam, WalletInsertQuery> {

    @Override
    public WalletInsertQuery convert(WalletAssignParam param) {
        return new WalletInsertQuery(param.getVoucherId().toString());
    }
}
