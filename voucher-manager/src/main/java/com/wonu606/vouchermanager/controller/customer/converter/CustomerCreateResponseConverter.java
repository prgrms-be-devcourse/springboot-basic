package com.wonu606.vouchermanager.controller.customer.converter;

import com.wonu606.vouchermanager.controller.customer.response.CustomerCreateResponse;
import com.wonu606.vouchermanager.service.customer.result.CustomerCreateResult;
import com.wonu606.vouchermanager.util.TypedConverter;
import org.springframework.core.convert.converter.Converter;

public class CustomerCreateResponseConverter implements
        TypedConverter<CustomerCreateResult, CustomerCreateResponse> {

    @Override
    public CustomerCreateResponse convert(CustomerCreateResult result) {
        return new CustomerCreateResponse(result.isTaskSuccess());
    }

    @Override
    public Class<CustomerCreateResult> getSourceType() {
        return CustomerCreateResult.class;
    }

    @Override
    public Class<CustomerCreateResponse> getTargetType() {
        return CustomerCreateResponse.class;
    }
}
