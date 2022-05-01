package org.prgms.management.controller.api;

import org.prgms.management.model.customer.Customer;
import org.prgms.management.service.customer.CustomerService;
import org.prgms.management.util.JdbcUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class CustomerRestController {
    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/api/v1/customers",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<Customer> getCustomerList() {
        return customerService.getAll();
    }

    @GetMapping(value = "/api/v1/customer/customerId",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Customer getCustomerById(@RequestParam UUID customerId) {
        return customerService.getById(customerId);
    }

    @GetMapping(value = "/api/v1/customer/name",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Customer getCustomerByName(@RequestParam String customerName) {
        return customerService.getByName(customerName);
    }

    @PutMapping("/api/v1/customer")
    public Customer createCustomer(@RequestParam String customerName) {
        return customerService.create(customerName);
    }

    @DeleteMapping("/api/v1/customer/delete")
    public Customer deleteCustomer(@RequestParam UUID customerId) {
        return customerService.delete(customerId);
    }
}
