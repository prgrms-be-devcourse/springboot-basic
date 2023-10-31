package com.pgms.part1.domain.customer.controller;

import com.pgms.part1.domain.customer.dto.CustomerCreateRequestDto;
import com.pgms.part1.domain.customer.dto.CustomerResponseDto;
import com.pgms.part1.domain.customer.service.CustomerService;
import com.pgms.part1.domain.voucher.dto.VoucherResponseDto;
import com.pgms.part1.domain.voucher.dto.VoucherWalletResponseDtos;
import com.pgms.part1.domain.voucher.service.VoucherService;
import com.pgms.part1.domain.wallet.dto.WalletCreateRequestDto;
import com.pgms.part1.domain.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CustomerWebController {

    private final Logger log = LoggerFactory.getLogger(CustomerWebController.class);
    private final CustomerService customerService;
    private final WalletService walletService;
    private final VoucherService voucherService;

    public CustomerWebController(CustomerService customerService, WalletService walletService, VoucherService voucherService) {
        this.customerService = customerService;
        this.walletService = walletService;
        this.voucherService = voucherService;
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

    @GetMapping("/customers/{customer_id}/wallets/add")
    public String getCustomerWalletAddPage(Model model, @PathVariable Long customer_id){
        model.addAttribute("userId", customer_id);
        List<VoucherResponseDto> voucherResponseDtos = voucherService.listVoucher();
        model.addAttribute("vouchers", voucherResponseDtos);
        return "customers/customersWalletAddPage";
    }

    @PostMapping("/customers/wallets/add")
    public String addCustomerWallet(WalletCreateRequestDto walletCreateRequestDto, RedirectAttributes redirectAttributes){
        walletService.addWallet(walletCreateRequestDto);
        Long id = walletCreateRequestDto.userId();
        redirectAttributes.addAttribute("id", id);
        return "redirect:/customers/{id}";
    }
}
