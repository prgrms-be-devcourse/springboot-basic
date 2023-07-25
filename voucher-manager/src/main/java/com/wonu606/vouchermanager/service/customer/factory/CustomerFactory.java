package com.wonu606.vouchermanager.service.customer.factory;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.email.Email;
import com.wonu606.vouchermanager.service.customer.param.CustomerCreateParam;
import org.springframework.stereotype.Component;

@Component
public class CustomerFactory {

    public Customer create(CustomerCreateParam param) {
        return new Customer(new Email(param.getEmail()), param.getNickname());
    }
}
