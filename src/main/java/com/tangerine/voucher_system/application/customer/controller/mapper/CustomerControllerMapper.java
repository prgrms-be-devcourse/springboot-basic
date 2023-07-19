package com.tangerine.voucher_system.application.customer.controller.mapper;

import com.tangerine.voucher_system.application.customer.controller.dto.CreateCustomerRequest;
import com.tangerine.voucher_system.application.customer.controller.dto.CustomerResponse;
import com.tangerine.voucher_system.application.customer.controller.dto.UpdateCustomerRequest;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerParam;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerControllerMapper {

    CustomerControllerMapper INSTANCE = Mappers.getMapper(CustomerControllerMapper.class);

    @Mapping(target = "customerId", expression = "java(java.util.UUID.randomUUID())")
    CustomerParam requestToParam(CreateCustomerRequest request);

    CustomerParam requestToParam(UpdateCustomerRequest request);

    CustomerResponse resultToResponse(CustomerResult result);

}
