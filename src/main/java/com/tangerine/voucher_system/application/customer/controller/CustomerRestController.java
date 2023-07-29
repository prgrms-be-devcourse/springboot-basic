package com.tangerine.voucher_system.application.customer.controller;

import com.tangerine.voucher_system.application.customer.controller.dto.CreateCustomerRequest;
import com.tangerine.voucher_system.application.customer.controller.dto.CustomerResponse;
import com.tangerine.voucher_system.application.customer.controller.dto.UpdateCustomerRequest;
import com.tangerine.voucher_system.application.customer.controller.mapper.CustomerControllerMapper;
import com.tangerine.voucher_system.application.customer.model.Name;
import com.tangerine.voucher_system.application.customer.service.CustomerService;
import org.springframework.http.MediaType;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> registerCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
        return ResponseEntity.ok(
                CustomerControllerMapper.INSTANCE.resultToResponse(
                        customerService.createCustomer(CustomerControllerMapper.INSTANCE.requestToParam(createCustomerRequest))));
    }
    
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> modifyCustomer(@RequestBody UpdateCustomerRequest updateCustomerRequest) {
        return ResponseEntity.ok(
                CustomerControllerMapper.INSTANCE.resultToResponse(
                        customerService.updateCustomer(CustomerControllerMapper.INSTANCE.requestToParam(updateCustomerRequest))));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerResponse>> getCustomerList(@RequestParam(name = "isBlack", defaultValue = "false") boolean isBlack) {
        return ResponseEntity.ok(
                CustomerControllerMapper.INSTANCE.resultsToResponses(
                        isBlack ? customerService.findBlackCustomers() : customerService.findAllCustomers()
                )
        );
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable("id") UUID customerId) {
        return ResponseEntity.ok(
                CustomerControllerMapper.INSTANCE.resultToResponse(
                        customerService.findCustomerById(customerId)));
    }
    
    @GetMapping(path = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerResponse>> getCustomerByName(@PathVariable(name = "name") String name) {
        return ResponseEntity.ok(
                CustomerControllerMapper.INSTANCE.resultsToResponses(
                        customerService.findCustomerByName(new Name(name))));
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> unregisterCustomerById(@PathVariable(name = "id") UUID customerId) {
        return ResponseEntity.ok(
                CustomerControllerMapper.INSTANCE.resultToResponse(
                        customerService.deleteCustomerById(customerId)));
    }

}
