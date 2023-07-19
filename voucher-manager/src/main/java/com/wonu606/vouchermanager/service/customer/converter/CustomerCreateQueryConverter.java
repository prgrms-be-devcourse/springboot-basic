package com.wonu606.vouchermanager.service.customer.converter;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.repository.customer.query.CustomerCreateQuery;
import com.wonu606.vouchermanager.util.TypedConverter;

public class CustomerCreateQueryConverter implements TypedConverter<Customer, CustomerCreateQuery> {

    @Override
    public CustomerCreateQuery convert(Customer entity) {
        return new CustomerCreateQuery(entity.getEmailAddress(), entity.getNickname());
    }

    @Override
    public Class<Customer> getSourceType() {
        return Customer.class;
    }

    @Override
    public Class<CustomerCreateQuery> getTargetType() {
        return CustomerCreateQuery.class;
    }
}
