package com.prgms.management.customer.controller;

import com.prgms.management.customer.dto.CustomerRequest;
import com.prgms.management.customer.dto.CustomerResponse;
import com.prgms.management.customer.model.Customer;
import com.prgms.management.customer.service.CustomerService;
import com.prgms.management.voucher.dto.VoucherResponse;
import com.prgms.management.voucher.model.Voucher;
import com.prgms.management.voucher_wallet.entity.VoucherWallet;
import com.prgms.management.voucher_wallet.repository.VoucherWalletRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("customers")
public class ViewCustomerController {
    private final CustomerService customerService;
    private final VoucherWalletRepository voucherWalletRepository;
    private final String MENU_TYPE = "CUSTOMER";

    public ViewCustomerController(CustomerService customerService, VoucherWalletRepository voucherWalletRepository) {
        this.customerService = customerService;
        this.voucherWalletRepository = voucherWalletRepository;
    }

    @GetMapping
    public String customerList(Model model) {
        List<Customer> customerList = customerService.findAllCustomers();
        List<CustomerResponse> list = customerList.stream().map(CustomerResponse::of).toList();
        model.addAttribute("customerList", list);
        model.addAttribute("menuType", MENU_TYPE);
        return "customer/customer_list";
    }

    @PostMapping
    public String customerAdd(CustomerRequest request) {
        Customer customer = customerService.addCustomer(request.toCustomer());
        return "redirect:/customers/" + customer.getId().toString();
    }

    @GetMapping("new")
    public String customerAddPage(Model model) {
        model.addAttribute("menuType", MENU_TYPE);
        return "customer/customer_add";
    }

    @GetMapping("{id}")
    public String voucherDetail(@PathVariable("id") UUID id, Model model) {
        Customer customer = customerService.findCustomerById(id);
        List<VoucherWallet> voucherWallet = voucherWalletRepository.findByCustomer(customer);
        List<Voucher> voucherList = voucherWallet.stream().map(VoucherWallet::getVoucher).toList();
        List<VoucherResponse> list = voucherList.stream().map(VoucherResponse::of).toList();
        model.addAttribute("customer", CustomerResponse.of(customer));
        model.addAttribute("voucherList", list);
        model.addAttribute("menuType", MENU_TYPE);
        return "customer/customer_detail";
    }

    @DeleteMapping("{id}")
    public String customerRemove(@PathVariable("id") UUID id) {
        customerService.removeCustomerById(id);
        return "redirect:/customers";
    }
}
