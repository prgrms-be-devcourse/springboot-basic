package kdt.vouchermanagement.domain.customer.controller;

import kdt.vouchermanagement.domain.customer.dto.CustomerCreateRequest;
import kdt.vouchermanagement.domain.customer.dto.CustomerResponse;
import kdt.vouchermanagement.domain.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCustomer(@RequestBody @Valid CustomerCreateRequest request) {
        customerService.saveCustomer(request.toDomain());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> getCustomers() {
        return customerService.findAllCustomers();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable long id) {
        customerService.deleteById(id);
    }
}
