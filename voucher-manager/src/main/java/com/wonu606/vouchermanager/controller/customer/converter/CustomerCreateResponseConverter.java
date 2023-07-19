package com.wonu606.vouchermanager.controller.customer.converter;

import com.wonu606.vouchermanager.controller.customer.response.CustomerCreateResponse;
import com.wonu606.vouchermanager.service.customer.result.CustomerCreateResult;
import org.springframework.core.convert.converter.Converter;

public class CustomerCreateResponseConverter implements Converter<CustomerCreateResult, CustomerCreateResponse> {

    @Override
    public CustomerCreateResponse convert(CustomerCreateResult result) {
        return new CustomerCreateResponse(result.isTaskSuccess());
    }
}
