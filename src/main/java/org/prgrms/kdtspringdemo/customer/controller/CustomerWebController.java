package org.prgrms.kdtspringdemo.customer.controller;

import org.prgrms.kdtspringdemo.customer.domain.Customer;
import org.prgrms.kdtspringdemo.customer.service.CustomerService;
import org.prgrms.kdtspringdemo.dto.CustomerRequestDto;
import org.prgrms.kdtspringdemo.dto.CustomerViewDto;
import org.prgrms.kdtspringdemo.wallet.service.WalletService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/customers")
public class CustomerWebController {
    private final CustomerService customerService;
    private final WalletService walletService;

    public CustomerWebController(CustomerService customerService, WalletService walletService) {
        this.customerService = customerService;
        this.walletService = walletService;
    }

    @GetMapping
    public String getAllCustomers(Model model) {
        List<Customer> customerList = customerService.findAll();
        List<CustomerViewDto> customerViewDtos = new ArrayList<>();
        customerList.forEach(customer -> customerViewDtos.add(new CustomerViewDto(customer)));
        List<CustomerViewDto> noneHaveWalletCustomers = customerService.findNoneHaveWalletCustomer();

        model.addAttribute("customerList", customerViewDtos);
        model.addAttribute("customers", noneHaveWalletCustomers);

        return "customer";
    }

    @PostMapping("/create")
    public String createCustomer(@ModelAttribute CustomerRequestDto customerRequestDto) {
        CustomerViewDto customer = customerService.insert(customerRequestDto);
        if (customer != null) walletService.create(customer.getCustomerId());
        return "redirect:/customers";
    }

    @GetMapping("/{customerId}/createWallet")
    public String createWalletForCustomer(@PathVariable UUID customerId) {
        walletService.create(customerId);
        return "redirect:/customers";
    }

    @GetMapping("/{customerId}/delete")
    public String deleteVoucher(@PathVariable UUID customerId) {
        customerService.deleteById(customerId);
        return "redirect:/customers";
    }
}
