package com.pgms.part1.domain.customer.controller;

import com.pgms.part1.domain.customer.dto.CustomerCreateRequestDto;
import com.pgms.part1.domain.customer.dto.CustomerResponseDto;
import com.pgms.part1.domain.customer.service.CustomerService;
import com.pgms.part1.domain.voucher.dto.VoucherWalletResponseDtos;
import com.pgms.part1.domain.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CustomerWebController {

    private final Logger log = LoggerFactory.getLogger(CustomerWebController.class);
    private final CustomerService customerService;
    private final WalletService walletService;

    public CustomerWebController(CustomerService customerService, WalletService walletService) {
        this.customerService = customerService;
        this.walletService = walletService;
    }

    @GetMapping("/")
    public String getMainPage(){
        return "index";
    }

    @GetMapping("/customers")
    public String getCustomersList(Model model){
        List<CustomerResponseDto> customerResponseDtos = customerService.listCustomers();
        model.addAttribute("customers", customerResponseDtos);
        return "customers/customersListPage";
    }

    @GetMapping("/customers/{id}/delete")
    public String deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }

    @PostMapping("/customers/create")
    public String createCustomer(CustomerCreateRequestDto customerCreateRequestDto){
        customerService.addCustomer(customerCreateRequestDto);
        return "redirect:/customers";
    }

    @GetMapping("/customers/create")
    public String getCreateCustomerPage(Model model){
        model.addAttribute("customerDto", new CustomerCreateRequestDto("", ""));
        return "customers/customersCreatePage";
    }

    @GetMapping("/customers/{id}")
    public String getCustomerWalletPage(@PathVariable Long id, Model model){
        List<VoucherWalletResponseDtos> voucherWalletResponseDtos = walletService.listVouchersByCustomer(id);
        model.addAttribute("vouchers", voucherWalletResponseDtos);
        model.addAttribute("customer_id", id);
        return "customers/customersDetailPage";
    }

    @GetMapping("/customers/{customer_id}/wallets/{wallet_id}/delete")
    public String deleteVoucherFromCustomer(@PathVariable Long customer_id,
                                            @PathVariable Long wallet_id){
        walletService.deleteWallet(wallet_id);
        return "redirect:/customers/{customer_id}";
    }
}
