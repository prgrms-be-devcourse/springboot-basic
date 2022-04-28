package com.mountain.voucherApp.adapter.in.web;

import com.mountain.voucherApp.application.port.in.VoucherAppUseCase;
import com.mountain.voucherApp.application.service.CustomerService;
import com.mountain.voucherApp.application.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {

    private final CustomerService customerService;
    private final VoucherService voucherService;
    private final VoucherAppUseCase voucherAppUseCase;

    public CustomerController(CustomerService customerService, VoucherService voucherService, VoucherAppUseCase voucherAppUseCase) {
        this.customerService = customerService;
        this.voucherService = voucherService;
        this.voucherAppUseCase = voucherAppUseCase;
    }

    @GetMapping("customers")
    public String customerListPage(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customer/customer-list";
    }

    @GetMapping("regist-voucher")
    public String registVoucherInfoPage(Model model) {
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("vouchers", voucherService.findAll());
        return "customer/regist-voucher-info";
    }
}
