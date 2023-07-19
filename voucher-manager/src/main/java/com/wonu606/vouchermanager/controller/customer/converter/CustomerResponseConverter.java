package com.wonu606.vouchermanager.controller.customer.converter;

import com.wonu606.vouchermanager.controller.customer.response.CustomerResponse;
import com.wonu606.vouchermanager.service.customer.result.CustomerResult;
import org.springframework.core.convert.converter.Converter;

public class CustomerResponseConverter implements Converter<CustomerResult, CustomerResponse> {

    @Override
    public CustomerResponse convert(CustomerResult result) {
        return new CustomerResponse(result.getEmail(), result.getNickname());
    }
}
