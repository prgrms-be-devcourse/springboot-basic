package com.prgrms.commandLineApplication.customer.dto;

import com.prgrms.commandLineApplication.customer.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

  public CustomerResponseDto mapToResponse(Customer customer) {
    return new CustomerResponseDto(customer.getCustomerId(), customer.getCustomerName(), customer.getEmail());
  }

}
