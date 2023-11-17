package org.prgms.springbootbasic.controller;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.controller.voucher.dto.VoucherResponseDto;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.service.CustomerService;
import org.prgms.springbootbasic.service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        List<Customer> customers = customerService.findAll(); //pagination
        List<VoucherResponseDto> vouchers = voucherService.findAll();

        model.addAttribute("customers", customers);
        model.addAttribute("vouchers", vouchers);

        return "index";
    }

    @GetMapping("/temp")
    @ResponseBody
    public ResponseEntity<String> statusSetting(){

        return ResponseEntity.status(302)
                .header("Location", "/")
                .body("body");
    }
}
