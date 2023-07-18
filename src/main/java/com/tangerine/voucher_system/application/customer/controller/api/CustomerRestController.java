package com.tangerine.voucher_system.application.customer.controller.api;

import com.tangerine.voucher_system.application.customer.controller.CustomerController;
import com.tangerine.voucher_system.application.customer.controller.converter.ControllerMapper;
import com.tangerine.voucher_system.application.customer.controller.dto.CreateCustomerRequest;
import com.tangerine.voucher_system.application.customer.controller.dto.CustomerResponse;
import com.tangerine.voucher_system.application.customer.controller.dto.UpdateCustomerRequest;
import com.tangerine.voucher_system.application.customer.model.Name;
import com.tangerine.voucher_system.application.customer.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestController implements CustomerController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/black")
    public ResponseEntity<List<CustomerResponse>> blackCustomerList() {
        return ResponseEntity.ok(
                customerService.findBlackCustomers()
                        .stream()
                        .map(ControllerMapper.INSTANCE::resultToResponse)
                        .toList()
        );
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> registerCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
        return ResponseEntity.ok(
                ControllerMapper.INSTANCE.resultToResponse(
                        customerService.createCustomer(ControllerMapper.INSTANCE.requestToParam(createCustomerRequest))));
    }

    @PostMapping("/update")
    public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody UpdateCustomerRequest updateCustomerRequest) {
        return ResponseEntity.ok(
                ControllerMapper.INSTANCE.resultToResponse(
                        customerService.updateCustomer(ControllerMapper.INSTANCE.requestToParam(updateCustomerRequest))));
    }

    @GetMapping("")
    public ResponseEntity<List<CustomerResponse>> customerList() {
        return ResponseEntity.ok(
                customerService.findAllCustomers()
                        .stream()
                        .map(ControllerMapper.INSTANCE::resultToResponse)
                        .toList()
        );
    }

    @GetMapping("/id/{customerId}")
    public ResponseEntity<CustomerResponse> customerById(@PathVariable("customerId") UUID customerId) {
        return ResponseEntity.ok(
                ControllerMapper.INSTANCE.resultToResponse(
                        customerService.findCustomerById(customerId)));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CustomerResponse> customerByName(@PathVariable("name") Name name) {
        return ResponseEntity.ok(
                ControllerMapper.INSTANCE.resultToResponse(
                        customerService.findCustomerByName(name)));
    }

    @GetMapping("/unregister/{customerId}")
    public ResponseEntity<CustomerResponse> unregisterCustomerById(@PathVariable("customerId") UUID customerId) {
        return ResponseEntity.ok(
                ControllerMapper.INSTANCE.resultToResponse(
                        customerService.deleteCustomerById(customerId)));
    }

}
