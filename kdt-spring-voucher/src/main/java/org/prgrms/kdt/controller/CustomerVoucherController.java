package org.prgrms.kdt.controller;

import org.prgrms.kdt.service.CustomerService;
import org.prgrms.kdt.service.CustomerVoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class CustomerVoucherController {

    private final CustomerVoucherService customerVoucherService;

    private final CustomerService customerService;

    public CustomerVoucherController(CustomerVoucherService customerVoucherService, CustomerService customerService) {
        this.customerVoucherService = customerVoucherService;
        this.customerService = customerService;
    }

    @GetMapping("/customer-vouchers")
    public String viewCustomerVoucherPage(Model model){
        var customerVoucherList = customerVoucherService.findAll();
        var notAllocateVoucherList = customerVoucherService.findNotAllocateVouchers();
        model.addAttribute("customerVouchers",customerVoucherList); //이거 약간 수정하기
        model.addAttribute("vouchers",notAllocateVoucherList);
        return "views/allocate-voucher";
    }

    @GetMapping("/customer-vouchers/{voucherId}")
    public String viewAllocateCustomerPage(@PathVariable("voucherId") UUID voucherId, Model model){
        var customerList = customerService.getAllCustomers();
        model.addAttribute("voucherId",voucherId);
        model.addAttribute("customers",customerList);
        return "views/allocate-customer";
    }

    @PostMapping("/customer-vouchers/{voucherId}/{customerId}")
    public String createCustomerVoucher(@PathVariable("voucherId") UUID voucherId,
                                        @PathVariable("customerId") UUID customerId){
        customerVoucherService.createAllocateVoucher(customerId,voucherId);
        return "redirect:/customer-vouchers";
    }
}
