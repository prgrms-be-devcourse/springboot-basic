package com.wonu606.vouchermanager.service.voucherwallet.converter;

import com.wonu606.vouchermanager.repository.voucherwallet.resultset.WalletInsertResultSet;
import com.wonu606.vouchermanager.service.voucherwallet.result.WalletAssignResult;
import org.springframework.core.convert.converter.Converter;

public class WalletInsertResultConverter implements
        Converter<WalletInsertResultSet, WalletAssignResult> {

    @Override
    public WalletAssignResult convert(WalletInsertResultSet param) {
        return new WalletAssignResult(param.getAffectedRowsCount() == 1);
    }
}
