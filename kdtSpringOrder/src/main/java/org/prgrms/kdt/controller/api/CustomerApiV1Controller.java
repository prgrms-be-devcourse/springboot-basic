package org.prgrms.kdt.controller.api;

import org.prgrms.kdt.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class CustomerApiV1Controller {

    private final CustomerService customerService;

    public CustomerApiV1Controller(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public ResponseEntity list() {
        return ResponseEntity.ok().body(customerService.getAllCustomers());
    }

}