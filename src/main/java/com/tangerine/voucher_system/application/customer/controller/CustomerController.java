package com.tangerine.voucher_system.application.customer.controller;

import com.tangerine.voucher_system.application.customer.controller.dto.CreateCustomerRequest;
import com.tangerine.voucher_system.application.customer.controller.dto.CustomerResponse;
import com.tangerine.voucher_system.application.customer.controller.dto.UpdateCustomerRequest;
import com.tangerine.voucher_system.application.customer.model.Name;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface CustomerController {

    ResponseEntity<CustomerResponse> registerCustomer(CreateCustomerRequest createCustomerRequest);

    ResponseEntity<CustomerResponse> updateCustomer(@RequestBody UpdateCustomerRequest updateCustomerRequest);

    ResponseEntity<List<CustomerResponse>> customerList();

    ResponseEntity<List<CustomerResponse>> blackCustomerList();

    ResponseEntity<CustomerResponse> customerById(UUID customerId);

    ResponseEntity<CustomerResponse> customerByName(Name name);

    ResponseEntity<CustomerResponse> unregisterCustomerById(UUID customerId);

}
