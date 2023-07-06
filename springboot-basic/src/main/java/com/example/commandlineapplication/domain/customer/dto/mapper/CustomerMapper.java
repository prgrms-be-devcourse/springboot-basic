package com.example.commandlineapplication.domain.customer.dto.mapper;

import com.example.commandlineapplication.domain.customer.dto.request.CustomerCreateRequest;
import com.example.commandlineapplication.domain.customer.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomerMapper {

  public Customer createRequestToCustomer(CustomerCreateRequest customerCreateRequest) {
    return new Customer(customerCreateRequest.getCustomerId(), customerCreateRequest.getName(),
        customerCreateRequest.getName());
  }
}
