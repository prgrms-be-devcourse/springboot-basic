package com.tangerine.voucher_system.application.customer.controller.mapper;

import com.tangerine.voucher_system.application.customer.controller.dto.CreateCustomerRequest;
import com.tangerine.voucher_system.application.customer.controller.dto.CustomerResponse;
import com.tangerine.voucher_system.application.customer.controller.dto.CustomerResponses;
import com.tangerine.voucher_system.application.customer.controller.dto.UpdateCustomerRequest;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerParam;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerResult;
import com.tangerine.voucher_system.application.global.generator.IdGenerator;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CustomerControllerMapper {

    CustomerControllerMapper INSTANCE = Mappers.getMapper(CustomerControllerMapper.class);

    @Mapping(target = "customerId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(source = "name", target = "name.value")
    CustomerParam requestToParam(CreateCustomerRequest request);

    @Mapping(source = "name", target = "name.value")
    CustomerParam requestToParam(UpdateCustomerRequest request);

    @Mapping(source = "name.value", target = "name")
    CustomerResponse resultToResponse(CustomerResult result);

    List<CustomerResponse> resultsToResponses(List<CustomerResult> customerResults);

}
