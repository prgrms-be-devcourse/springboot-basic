package com.wonu606.vouchermanager.service.voucherwallet.converter;

import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedCustomerResultSet;
import com.wonu606.vouchermanager.service.voucherwallet.result.OwnedCustomerResult;
import org.springframework.core.convert.converter.Converter;

public class OwnedCustomersResultConverter implements Converter<OwnedCustomerResultSet, OwnedCustomerResult> {

    @Override
    public OwnedCustomerResult convert(OwnedCustomerResultSet param) {
        return new OwnedCustomerResult(param.getCustomerId());
    }
}
