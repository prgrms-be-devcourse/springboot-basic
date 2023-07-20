package com.wonu606.vouchermanager.service.voucherwallet.converter;

import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletInsertQuery;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletAssignParam;
import com.wonu606.vouchermanager.util.TypedConverter;

public class WalletInsertQueryConverter implements
        TypedConverter<WalletAssignParam, WalletInsertQuery> {

    @Override
    public WalletInsertQuery convert(WalletAssignParam param) {
        return new WalletInsertQuery(param.getVoucherId().toString());
    }

    @Override
    public Class<WalletAssignParam> getSourceType() {
        return WalletAssignParam.class;
    }

    @Override
    public Class<WalletInsertQuery> getTargetType() {
        return WalletInsertQuery.class;
    }
}
