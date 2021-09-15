package org.prgrms.kdt.controller;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.service.CustomerService;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
public class CustomerController {

    private final CustomerService customerService;
    private final VoucherService voucherService;

    public CustomerController(CustomerService customerService, VoucherService voucherService) {
        this.customerService = customerService;
        this.voucherService = voucherService;
    }

    @GetMapping("/customers")
    public String list(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "views/customers";
    }

    @GetMapping("/customers/new")
    public String createForm(Model model){
        model.addAttribute("customerForm", new CustomerForm());
        return "views/new-customers";
    }

    @PostMapping("/customers/new")
    public String createForm(@Valid CustomerForm customerForm, BindingResult result){
        if(result.hasErrors()) {
            return "views/new-customers";
        }

        customerService.createCustomer(customerForm.getName(), customerForm.getEmail());
        return "redirect:/customers";
    }

    @GetMapping("/customers/{customerId}")
    public String findVoucherByCustomerId(@PathVariable("customerId") UUID customerId, Model model) {
        List<Voucher> VoucherList = voucherService.getVouchersByCustomerId(customerId);
        if(VoucherList.isEmpty()) {
            return "views/404";
        }

        model.addAttribute("vouchers", VoucherList);
        return "views/customer-detail";
    }

    @PostMapping("/customers/{customerId}/vouchers/{voucherId}")
    public String deleteVoucherByCustomerId(@PathVariable("customerId") UUID customerId, @PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteVoucher(customerId, voucherId);
        return "redirect:/customers";
    }

}
