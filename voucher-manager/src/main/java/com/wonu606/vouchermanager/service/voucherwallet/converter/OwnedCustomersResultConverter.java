package com.wonu606.vouchermanager.service.voucherwallet.converter;

import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedCustomerResultSet;
import com.wonu606.vouchermanager.service.voucherwallet.result.OwnedCustomerResult;
import com.wonu606.vouchermanager.util.TypedConverter;

public class OwnedCustomersResultConverter implements
        TypedConverter<OwnedCustomerResultSet, OwnedCustomerResult> {

    @Override
    public OwnedCustomerResult convert(OwnedCustomerResultSet param) {
        return new OwnedCustomerResult(param.getCustomerId());
    }

    @Override
    public Class<OwnedCustomerResultSet> getSourceType() {
        return OwnedCustomerResultSet.class;
    }

    @Override
    public Class<OwnedCustomerResult> getTargetType() {
        return OwnedCustomerResult.class;
    }
}
