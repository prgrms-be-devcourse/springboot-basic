package com.wonu606.vouchermanager.service.customer.creator;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.email.Email;
import com.wonu606.vouchermanager.service.customer.param.CustomerCreateParam;

public class CustomerCreator {

    public Customer create(CustomerCreateParam param) {
        return new Customer(new Email(param.getEmail()), param.getNickname());
    }
}
