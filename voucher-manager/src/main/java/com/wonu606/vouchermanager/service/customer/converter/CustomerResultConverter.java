package com.wonu606.vouchermanager.service.customer.converter;

import com.wonu606.vouchermanager.repository.customer.resultset.CustomerResultSet;
import com.wonu606.vouchermanager.service.customer.result.CustomerResult;
import com.wonu606.vouchermanager.util.TypedConverter;

public class CustomerResultConverter implements TypedConverter<CustomerResultSet, CustomerResult> {

    @Override
    public CustomerResult convert(CustomerResultSet resultSet) {
        return new CustomerResult(resultSet.getEmail(), resultSet.getNickname());
    }

    @Override
    public Class<CustomerResultSet> getSourceType() {
        return CustomerResultSet.class;
    }

    @Override
    public Class<CustomerResult> getTargetType() {
        return CustomerResult.class;
    }
}
