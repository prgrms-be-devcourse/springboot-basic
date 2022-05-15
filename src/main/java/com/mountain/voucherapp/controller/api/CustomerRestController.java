package com.mountain.voucherapp.controller.api;

import com.mountain.voucherapp.service.VoucherAppService;
import com.mountain.voucherapp.service.customer.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestController {

    private final CustomerService customerService;
    private final VoucherAppService voucherAppService;

    public CustomerRestController(CustomerService customerService, VoucherAppService voucherAppService) {
        this.customerService = customerService;
        this.voucherAppService = voucherAppService;
    }

    @GetMapping
    public ResponseEntity customers() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/vouchers/{voucherId}")
    public ResponseEntity customersByVoucherId(@PathVariable String voucherId) {
        return ResponseEntity.ok(voucherAppService.showByVoucher(UUID.fromString(voucherId)));
    }
}
