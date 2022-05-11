package com.waterfogsw.voucher.customer.controller;

import com.waterfogsw.voucher.customer.controller.dto.CustomerAddRequest;
import com.waterfogsw.voucher.customer.controller.dto.CustomerResponse;
import com.waterfogsw.voucher.customer.model.Customer;
import com.waterfogsw.voucher.customer.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/v1/customers")
@RestController
public class CustomerApiController {
    private final CustomerService customerService;

    public CustomerApiController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public void addCustomer(@Valid @RequestBody CustomerAddRequest request) {
        customerService.addCustomer(Customer.from(request));
    }

    @GetMapping
    public List<CustomerResponse> findAll() {
        return customerService.findAllCustomer()
                .stream()
                .map(CustomerResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public CustomerResponse findById(@PathVariable("id") long id) {
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
        final var findCustomer = customerService.findById(id);
        return CustomerResponse.from(findCustomer);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") long id) {
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
        customerService.deleteById(id);
    }

}
