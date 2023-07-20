package com.wonu606.vouchermanager.service.voucherwallet.converter;

import com.wonu606.vouchermanager.repository.voucherwallet.resultset.WalletInsertResultSet;
import com.wonu606.vouchermanager.service.voucherwallet.result.WalletAssignResult;
import com.wonu606.vouchermanager.util.TypedConverter;

public class WalletInsertResultConverter implements
        TypedConverter<WalletInsertResultSet, WalletAssignResult> {

    @Override
    public WalletAssignResult convert(WalletInsertResultSet param) {
        return new WalletAssignResult(param.getAffectedRowsCount() == 1);
    }

    @Override
    public Class<WalletInsertResultSet> getSourceType() {
        return WalletInsertResultSet.class;
    }

    @Override
    public Class<WalletAssignResult> getTargetType() {
        return WalletAssignResult.class;
    }
}
