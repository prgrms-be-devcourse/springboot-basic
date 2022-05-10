package org.prgrms.voucher.controller.web;


import org.prgrms.voucher.dto.CustomerRequestDto;
import org.prgrms.voucher.dto.CustomerResponseDto;
import org.prgrms.voucher.service.CustomerService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerWebController {

    private final CustomerService customerService;

    public CustomerWebController(CustomerService customerService) {

        this.customerService = customerService;
    }

    @PostMapping
    public void createCustomer(@RequestBody @Valid CustomerRequestDto request) {

       customerService.create(request.toDomain());
    }

    @GetMapping
    public List<CustomerResponseDto> findCustomers(@RequestParam("after") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable LocalDate after,
                                                   @RequestParam("before") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  @Nullable LocalDate before
    ) {
        if (after != null && before == null || after == null && before != null) {
            throw new IllegalArgumentException();
        }
        if (after != null) {
            return customerService.getCustomersByTerm(after, before).stream()
                    .map(CustomerResponseDto::from)
                    .toList();
        }

        return customerService.getCustomers().stream()
                .map(CustomerResponseDto::from)
                .toList();
    }

    @GetMapping("/{customerId}")
    public CustomerResponseDto findCustomerById(@PathVariable("customerId") Long customerId) {

        return CustomerResponseDto.from(customerService.getCustomer(customerId));
    }

    @DeleteMapping("/{customerId}")
    public void deleteCustomerById(@PathVariable Long customerId) {

        customerService.deleteCustomerById(customerId);
    }
}