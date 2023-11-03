package com.prgrms.vouchermanagement.core.customer.controller;

import com.prgrms.vouchermanagement.core.customer.controller.request.CustomerCreateRequest;
import com.prgrms.vouchermanagement.core.customer.controller.response.CustomerResponse;
import com.prgrms.vouchermanagement.core.customer.controller.response.CustomersResponse;
import com.prgrms.vouchermanagement.core.customer.dto.CustomerDto;
import com.prgrms.vouchermanagement.core.customer.service.CustomerService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CustomerController {

    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * 고객 등록
     *
     * @param customerCreateRequest
     */
    public void createCustomer(CustomerCreateRequest customerCreateRequest) {
        CustomerDto customerDto = new CustomerDto(customerCreateRequest.getName(), customerCreateRequest.getEmail());
        customerService.create(customerDto);
    }

    /**
     * 고객 전체 조회
     *
     * @return CustomersResponse
     */
    public CustomersResponse findAll() {
        List<CustomerDto> customerDtoList = customerService.findAll();
        return toCustomersResponse(customerDtoList);
    }

    private static CustomersResponse toCustomersResponse(List<CustomerDto> customerDtoList) {
        List<CustomerResponse> customerResponses = customerDtoList.stream()
                .map(it -> new CustomerResponse(it.getId(), it.getName(), it.getEmail()))
                .collect(Collectors.toList());
        return new CustomersResponse(customerResponses);
    }

}
