package com.mountain.voucherApp.controller.api;

import com.mountain.voucherApp.dto.CustomerDto;
import com.mountain.voucherApp.service.VoucherAppService;
import com.mountain.voucherApp.service.customer.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
    public List<CustomerDto> customers() {
        return customerService.findAll();
    }

    @GetMapping("/{voucherId}")
    public List<CustomerDto> customersByVoucherId(@PathVariable String voucherId) {
        return voucherAppService.showByVoucher(UUID.fromString(voucherId));
    }
}
