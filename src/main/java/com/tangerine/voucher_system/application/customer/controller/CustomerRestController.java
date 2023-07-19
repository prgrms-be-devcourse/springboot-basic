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

    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> registerCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
        return ResponseEntity.ok(
                CustomerControllerMapper.INSTANCE.resultToResponse(
                        customerService.createCustomer(CustomerControllerMapper.INSTANCE.requestToParam(createCustomerRequest))));
    }
    
    @PostMapping("/update")
    public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody UpdateCustomerRequest updateCustomerRequest) {
        return ResponseEntity.ok(
                CustomerControllerMapper.INSTANCE.resultToResponse(
                        customerService.updateCustomer(CustomerControllerMapper.INSTANCE.requestToParam(updateCustomerRequest))));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerResponse>> customerList() {
        return ResponseEntity.ok(
                customerService.findAllCustomers()
                        .stream()
                        .map(CustomerControllerMapper.INSTANCE::resultToResponse)
                        .toList()
        );
    }

    @GetMapping("/black")
    public ResponseEntity<List<CustomerResponse>> blackCustomerList() {
        return ResponseEntity.ok(
                customerService.findBlackCustomers()
                        .stream()
                        .map(CustomerControllerMapper.INSTANCE::resultToResponse)
                        .toList()
        );
    }

    @GetMapping("/id/{customerId}")
    public ResponseEntity<CustomerResponse> customerById(@PathVariable("customerId") UUID customerId) {
        return ResponseEntity.ok(
                CustomerControllerMapper.INSTANCE.resultToResponse(
                        customerService.findCustomerById(customerId)));
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<CustomerResponse> customerByName(@PathVariable("name") Name name) {
        return ResponseEntity.ok(
                CustomerControllerMapper.INSTANCE.resultToResponse(
                        customerService.findCustomerByName(name)));
    }

    @DeleteMapping("/unregister/{customerId}")
    public ResponseEntity<CustomerResponse> unregisterCustomerById(@PathVariable("customerId") UUID customerId) {
        return ResponseEntity.ok(
                CustomerControllerMapper.INSTANCE.resultToResponse(
                        customerService.deleteCustomerById(customerId)));
    }

}
