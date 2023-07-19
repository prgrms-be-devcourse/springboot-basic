package com.wonu606.vouchermanager.service.customer.converter;

import com.wonu606.vouchermanager.repository.customer.resultset.CustomerCreateResultSet;
import com.wonu606.vouchermanager.service.customer.result.CustomerCreateResult;
import com.wonu606.vouchermanager.util.TypedConverter;

public class CustomerCreateResultConverter implements
        TypedConverter<CustomerCreateResultSet, CustomerCreateResult> {

    @Override
    public CustomerCreateResult convert(CustomerCreateResultSet resultSet) {
        return new CustomerCreateResult(resultSet.getTaskSuccess() == 1);
    }

    @Override
    public Class<CustomerCreateResultSet> getSourceType() {
        return CustomerCreateResultSet.class;
    }

    @Override
    public Class<CustomerCreateResult> getTargetType() {
        return CustomerCreateResult.class;
    }
}
