package org.prgrms.vouchermanager.handler.customer;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.domain.customer.Customer;
import org.prgrms.vouchermanager.dto.CustomerRequest;
import org.prgrms.vouchermanager.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class ApiCustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerRequest>> findAll() {
        return new ResponseEntity<List<CustomerRequest>>(customerService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerRequest> createCustomer(@RequestBody CustomerRequest request){
        return new ResponseEntity<CustomerRequest>(customerService.createCustomer(request), HttpStatus.OK);
    }
}
