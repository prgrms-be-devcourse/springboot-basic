package com.wonu606.vouchermanager.service.customer.converter;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.repository.customer.query.CustomerCreateQuery;
import org.springframework.core.convert.converter.Converter;

public class CustomerCreateQueryConverter implements Converter<Customer, CustomerCreateQuery> {

    @Override
    public CustomerCreateQuery convert(Customer entity) {
        return new CustomerCreateQuery(entity.getEmailAddress(), entity.getNickname());
    }
}
