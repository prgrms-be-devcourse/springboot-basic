package com.mountain.voucherApp.controller;

import com.mountain.voucherApp.dto.CustomerDto;
import com.mountain.voucherApp.dto.VoucherIdUpdateDto;
import com.mountain.voucherApp.service.VoucherAppService;
import com.mountain.voucherApp.service.customer.CustomerService;
import com.mountain.voucherApp.service.voucher.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class CustomerController {

    private static final String REGIST_SUCCESS_MESSAGE = "바우처 정보가 등록되었습니다.";
    private static final String REMOVE_SUCCESS_MESSAGE = "바우처 정보가 삭제되었습니다.";

    private final CustomerService customerService;
    private final VoucherService voucherService;
    private final VoucherAppService voucherAppService;

    public CustomerController(CustomerService customerService, VoucherService voucherService, VoucherAppService voucherAppService) {
        this.customerService = customerService;
        this.voucherService = voucherService;
        this.voucherAppService = voucherAppService;
    }

    @GetMapping("customers")
    public String customersPage(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customer/customer-list";
    }

    @GetMapping("customers/{voucherId}")
    public String customersByVoucherId(@PathVariable String voucherId, Model model) {
        List<CustomerDto> customers = voucherAppService.showByVoucher(UUID.fromString(voucherId));
        model.addAttribute("customers", customers);
        return "customer/customer-list";
    }


    @GetMapping("regist-voucher")
    public String registVoucherInfoPage(Model model) {
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("vouchers", voucherService.findAll());
        return "customer/regist-voucher-info";
    }

    @GetMapping("regist-voucher-success")
    public String registVoucherSuccess(Model model) {
        model.addAttribute("message", REGIST_SUCCESS_MESSAGE);
        return "common/success";
    }

    @GetMapping("remove-voucher-success")
    public String removeVoucherSuccess(Model model) {
        model.addAttribute("message", REMOVE_SUCCESS_MESSAGE);
        return "common/success";
    }

    @GetMapping("remove-voucher")
    public String removeVoucherPage(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customer/remove-voucher-info";
    }

    @PostMapping("remove-voucher")
    public String removeVoucher(String customerId) {
        voucherAppService.removeVoucher(UUID.fromString(customerId));
        return "redirect:/remove-voucher-success";
    }

    @PostMapping("regist-voucher")
    public String registVoucher(VoucherIdUpdateDto voucherIdUpdateDto) {
        voucherAppService.addVoucher(voucherIdUpdateDto);
        return "redirect:/regist-voucher-success";
    }
}
