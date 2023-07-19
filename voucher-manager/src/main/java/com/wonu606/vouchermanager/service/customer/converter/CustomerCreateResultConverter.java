package com.wonu606.vouchermanager.service.customer.converter;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.repository.customer.query.CustomerCreateQuery;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerCreateResultSet;
import com.wonu606.vouchermanager.service.customer.result.CustomerCreateResult;
import org.springframework.core.convert.converter.Converter;

public class CustomerCreateResultConverter implements Converter<CustomerCreateResultSet, CustomerCreateResult> {

    @Override
    public CustomerCreateResult convert(CustomerCreateResultSet resultSet) {
        return new CustomerCreateResult(resultSet.getTaskSuccess() == 1);
    }
}
