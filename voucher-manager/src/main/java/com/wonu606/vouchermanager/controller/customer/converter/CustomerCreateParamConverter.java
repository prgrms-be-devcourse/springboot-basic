package com.wonu606.vouchermanager.controller.customer.converter;

import com.wonu606.vouchermanager.controller.customer.request.CustomerCreateRequest;
import com.wonu606.vouchermanager.service.customer.param.CustomerCreateParam;
import com.wonu606.vouchermanager.util.TypedConverter;

public class CustomerCreateParamConverter implements
        TypedConverter<CustomerCreateRequest, CustomerCreateParam> {

    public CustomerCreateParam convert(CustomerCreateRequest request) {
        return new CustomerCreateParam(request.getEmail(), request.getNickname());
    }

    @Override
    public Class<CustomerCreateRequest> getSourceType() {
        return CustomerCreateRequest.class;
    }

    @Override
    public Class<CustomerCreateParam> getTargetType() {
        return CustomerCreateParam.class;
    }
}
