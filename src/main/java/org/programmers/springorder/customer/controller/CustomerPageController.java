package org.programmers.springorder.customer.controller;

import org.programmers.springorder.customer.dto.CustomerRequestDto;
import org.programmers.springorder.customer.dto.CustomerResponseDto;
import org.programmers.springorder.customer.service.CustomerService;
import org.programmers.springorder.voucher.dto.VoucherResponseDto;
import org.programmers.springorder.voucher.service.VoucherService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Profile("prod")
@Controller
public class CustomerPageController {

    private final CustomerService customerService;
    private final VoucherService voucherService;

    public CustomerPageController(CustomerService customerService, VoucherService voucherService) {
        this.customerService = customerService;
        this.voucherService = voucherService;
    }

    @GetMapping("/customers")
    public String getCustomerListPage(Model model){
        List<CustomerResponseDto> allCustomers = customerService.getAllCustomers();
        model.addAttribute("customerList", allCustomers);
        return "customers";
    }

    @GetMapping("/blackCustomers")
    public String getBlackCustomerListPage(Model model){
        List<CustomerResponseDto> allCustomers = customerService.getBlackList();
        model.addAttribute("customerList", allCustomers);
        return "customers";
    }

    @GetMapping("/customers/{customerId}")
    public String getCustomerListPage(@PathVariable UUID customerId, Model model){
        CustomerResponseDto customer = customerService.findCustomer(customerId);
        List<VoucherResponseDto> customerOwnedVouchers = voucherService.getCustomerOwnedVouchers(customerId);
        model.addAttribute("customer", customer);
        model.addAttribute("customerVoucherList", customerOwnedVouchers);
        return "customer-detail";
    }

    @GetMapping("/new-customer")
    public String getNewCustomerPage(){
        return "new-customer";
    }

    @PostMapping("/customers")
    public String enrollCustomer(CustomerRequestDto requestDto){
        customerService.newCustomer(requestDto);
        return "redirect:/customers";
    }

}
