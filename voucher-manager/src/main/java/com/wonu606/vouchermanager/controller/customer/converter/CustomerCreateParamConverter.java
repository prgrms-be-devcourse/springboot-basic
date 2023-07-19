package com.wonu606.vouchermanager.controller.customer.converter;

import com.wonu606.vouchermanager.controller.customer.request.CustomerCreateRequest;
import com.wonu606.vouchermanager.service.customer.param.CustomerCreateParam;
import org.springframework.core.convert.converter.Converter;

public class CustomerCreateParamConverter implements Converter<CustomerCreateRequest, CustomerCreateParam> {

    public CustomerCreateParam convert(CustomerCreateRequest request) {
        return new CustomerCreateParam(request.getEmail(), request.getNickname());
    }
}
