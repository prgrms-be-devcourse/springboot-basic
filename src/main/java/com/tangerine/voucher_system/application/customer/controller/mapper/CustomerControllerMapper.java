package com.tangerine.voucher_system.application.customer.controller.mapper;

import com.tangerine.voucher_system.application.customer.controller.dto.CreateCustomerRequest;
import com.tangerine.voucher_system.application.customer.controller.dto.CustomerResponse;
import com.tangerine.voucher_system.application.customer.controller.dto.CustomerResponses;
import com.tangerine.voucher_system.application.customer.controller.dto.UpdateCustomerRequest;
import com.tangerine.voucher_system.application.customer.model.Name;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerParam;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerResult;
import com.tangerine.voucher_system.application.global.generator.IdGenerator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerControllerMapper {

    private final IdGenerator generator;

    public CustomerControllerMapper(IdGenerator generator) {
        this.generator = generator;
    }

    public CustomerParam requestToParam(CreateCustomerRequest request) {
        return new CustomerParam(
                generator.getUuid(),
                new Name(request.name()),
                request.isBlack()
        );
    }

    public CustomerParam requestToParam(UpdateCustomerRequest request) {
        return new CustomerParam(
                request.customerId(),
                new Name(request.name()),
                request.isBlack()
        );
    }

    public CustomerResponse resultToResponse(CustomerResult result) {
        return new CustomerResponse(
                result.customerId(),
                result.name().getValue(),
                result.isBlack()
        );
    }

    public CustomerResponses resultsToResponses(List<CustomerResult> customerResults) {
        return new CustomerResponses(
                customerResults.stream()
                        .map(this::resultToResponse)
                        .toList()
        );
    }

}
