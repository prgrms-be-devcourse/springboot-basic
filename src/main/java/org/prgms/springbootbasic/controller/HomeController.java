package org.prgms.springbootbasic.controller;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.service.CustomerService;
import org.prgms.springbootbasic.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
public class HomeController {
    private final CustomerService customerService;
    private final VoucherService voucherService;

    public HomeController(CustomerService customerService, VoucherService voucherService) {
        this.customerService = customerService;
        this.voucherService = voucherService;
    }

    @GetMapping
    public String homePage(Model model){
        List<Customer> customers = customerService.findAll();
        List<Voucher> vouchers = voucherService.findAll();

        model.addAttribute("customers", customers);
        model.addAttribute("vouchers", vouchers);

        log.info(customers.get(0).toString());

        return "index.html";
    }
}
