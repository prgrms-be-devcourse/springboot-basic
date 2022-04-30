package com.mountain.voucherApp.adapter.in.web.api;

import com.mountain.voucherApp.application.port.in.CustomerDto;
import com.mountain.voucherApp.application.port.in.VoucherAppUseCase;
import com.mountain.voucherApp.application.port.in.VoucherIdUpdateDto;
import com.mountain.voucherApp.application.service.CustomerService;
import com.mountain.voucherApp.application.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestController {

    private final CustomerService customerService;
    private final VoucherAppUseCase voucherAppUseCase;

    public CustomerRestController(CustomerService customerService, VoucherAppUseCase voucherAppUseCase) {
        this.customerService = customerService;
        this.voucherAppUseCase = voucherAppUseCase;
    }

    @GetMapping
    public List<CustomerDto> customers() {
        return customerService.findAll();
    }

    @GetMapping("/{voucherId}")
    public List<CustomerDto> customersByVoucherId(@RequestBody String voucherId) {
        return voucherAppUseCase.showByVoucher(UUID.fromString(voucherId));
    }
}
