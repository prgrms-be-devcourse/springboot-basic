package com.dojinyou.devcourse.voucherapplication.customer;


import com.dojinyou.devcourse.voucherapplication.customer.domain.Customer;
import com.dojinyou.devcourse.voucherapplication.customer.dto.CustomerCreateRequest;
import com.dojinyou.devcourse.voucherapplication.customer.dto.CustomerDefaultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerApiController {

    private final CustomerService customerService;

    public CustomerApiController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDefaultResponse create(@RequestBody CustomerCreateRequest customerCreateRequest) {
        return customerService.create(Customer.from(customerCreateRequest)).toDefaultResponse();
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDefaultResponse> findAll() {
        return customerService.findAll()
                              .stream()
                              .map(Customer::toDefaultResponse)
                              .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDefaultResponse findById(@PathVariable long id) {
        return customerService.findById(id).toDefaultResponse();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) {
        customerService.deleteById(id);
    }
}
