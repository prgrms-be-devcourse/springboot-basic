package com.wonu606.vouchermanager.controller.customer.converter;

import com.wonu606.vouchermanager.controller.customer.request.CustomerCreateRequest;
import com.wonu606.vouchermanager.service.customer.param.CustomerCreateParam;

public class CustomerCreateParamConverter {

    public CustomerCreateParam convert(CustomerCreateRequest request) {
        return new CustomerCreateParam(request.getEmail(), request.getNickname());
    }
}
