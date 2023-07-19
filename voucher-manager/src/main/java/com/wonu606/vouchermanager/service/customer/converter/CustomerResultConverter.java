package com.wonu606.vouchermanager.service.customer.converter;

import com.wonu606.vouchermanager.repository.customer.resultset.CustomerResultSet;
import com.wonu606.vouchermanager.service.customer.result.CustomerResult;
import org.springframework.core.convert.converter.Converter;

public class CustomerResultConverter implements Converter<CustomerResultSet, CustomerResult> {

    @Override
    public CustomerResult convert(CustomerResultSet resultSet) {
        return new CustomerResult(resultSet.getEmail(), resultSet.getNickname());
    }
}
