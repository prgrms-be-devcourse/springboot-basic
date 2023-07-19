package com.wonu606.vouchermanager.controller.customer.converter;

import com.wonu606.vouchermanager.controller.customer.response.CustomerResponse;
import com.wonu606.vouchermanager.service.customer.result.CustomerResult;
import com.wonu606.vouchermanager.util.TypedConverter;

public class CustomerResponseConverter implements TypedConverter<CustomerResult, CustomerResponse> {

    @Override
    public CustomerResponse convert(CustomerResult result) {
        return new CustomerResponse(result.getEmail(), result.getNickname());
    }

    @Override
    public Class<CustomerResult> getSourceType() {
        return CustomerResult.class;
    }

    @Override
    public Class<CustomerResponse> getTargetType() {
        return CustomerResponse.class;
    }
}
