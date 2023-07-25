package com.wonu606.vouchermanager.service.voucherwallet.converter;

import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedVoucherResultSet;
import com.wonu606.vouchermanager.service.voucherwallet.result.OwnedVoucherResult;
import com.wonu606.vouchermanager.util.TypedConverter;

public class OwnedVoucherResultConverter implements
        TypedConverter<OwnedVoucherResultSet, OwnedVoucherResult> {

    public OwnedVoucherResult convert(OwnedVoucherResultSet resultSet) {
        return new OwnedVoucherResult(resultSet.getVoucherId());
    }

    @Override
    public Class<OwnedVoucherResultSet> getSourceType() {
        return OwnedVoucherResultSet.class;
    }

    @Override
    public Class<OwnedVoucherResult> getTargetType() {
        return OwnedVoucherResult.class;
    }
}
