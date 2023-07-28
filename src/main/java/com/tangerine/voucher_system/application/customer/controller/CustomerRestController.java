package com.tangerine.voucher_system.application.customer.controller;

import com.tangerine.voucher_system.application.customer.controller.dto.CreateCustomerRequest;
import com.tangerine.voucher_system.application.customer.controller.dto.CustomerResponse;
import com.tangerine.voucher_system.application.customer.controller.dto.UpdateCustomerRequest;
import com.tangerine.voucher_system.application.customer.controller.mapper.CustomerControllerMapper;
import com.tangerine.voucher_system.application.customer.model.Name;
import com.tangerine.voucher_system.application.customer.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("")
    public ResponseEntity<CustomerResponse> registerCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
        return ResponseEntity.ok(
                CustomerControllerMapper.INSTANCE.resultToResponse(
                        customerService.createCustomer(CustomerControllerMapper.INSTANCE.requestToParam(createCustomerRequest))));
    }
    
    @PatchMapping("")
    public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody UpdateCustomerRequest updateCustomerRequest) {
        return ResponseEntity.ok(
                CustomerControllerMapper.INSTANCE.resultToResponse(
                        customerService.updateCustomer(CustomerControllerMapper.INSTANCE.requestToParam(updateCustomerRequest))));
    }

    @GetMapping("")
    public ResponseEntity<List<CustomerResponse>> customerList() {
        return ResponseEntity.ok(
                CustomerControllerMapper.INSTANCE.resultsToResponses(customerService.findAllCustomers())
        );
    }

    @GetMapping("/black")
    public ResponseEntity<List<CustomerResponse>> blackCustomerList() {
        return ResponseEntity.ok(
                CustomerControllerMapper.INSTANCE.resultsToResponses(customerService.findBlackCustomers())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> customerById(@PathVariable("id") UUID customerId) {
        return ResponseEntity.ok(
                CustomerControllerMapper.INSTANCE.resultToResponse(
                        customerService.findCustomerById(customerId)));
    }
    
    @GetMapping("/name")
    public ResponseEntity<CustomerResponse> customerByName(@RequestParam(name = "name") Name name) {
        return ResponseEntity.ok(
                CustomerControllerMapper.INSTANCE.resultToResponse(
                        customerService.findCustomerByName(name)));
    }

    @DeleteMapping("")
    public ResponseEntity<CustomerResponse> unregisterCustomerById(@RequestParam(name = "id") UUID customerId) {
        return ResponseEntity.ok(
                CustomerControllerMapper.INSTANCE.resultToResponse(
                        customerService.deleteCustomerById(customerId)));
    }

}
