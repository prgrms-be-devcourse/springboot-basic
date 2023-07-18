package com.wonu606.vouchermanager.service.voucherwallet.converter;

import com.wonu606.vouchermanager.repository.voucherwallet.resultset.WalletInsertResultSet;
import com.wonu606.vouchermanager.service.voucherwallet.result.WalletAssignResultSet;
import org.springframework.core.convert.converter.Converter;

public class WalletInsertResultConverter implements Converter<WalletInsertResultSet, WalletAssignResultSet> {

    @Override
    public WalletAssignResultSet convert(WalletInsertResultSet param) {
        return new WalletAssignResultSet(param.getAffectedRowsCount() == 1);
    }
}
