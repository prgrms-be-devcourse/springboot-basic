package org.prgrms.kdt.customer;

import org.prgrms.kdt.wallet.WalletService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    private final WalletService walletService;

    public CustomerController(CustomerService customerService, WalletService walletService) {
        this.customerService = customerService;
        this.walletService = walletService;
    }

    @GetMapping("/customers")
    public String viewCustomersPage(Model model){
        var allCustomers = customerService.getAllCustomer();
        model.addAttribute("customers", allCustomers);
        return "/customers";
    }

    @GetMapping("/customers/{customerId}")
    public String viewCustomerDetail(@PathVariable("customerId") UUID customerId, Model model){
        var wallets = walletService.finsWalletsByCustomerId(customerId);
        var vouchers = walletService.findVouchersByCustomerId(customerId);

        model.addAttribute("customerId", customerId);
        model.addAttribute("wallets", wallets);
        model.addAttribute("vouchers",vouchers);
        return "/customer-details";
    }

    @GetMapping("/customers/new")
    public String viewNewCustomerPage(){
        return "/new-customers";
    }

    @PostMapping (path = "/customers/new")
    public String addNewCustomer(createCustomerRequest createCustomerRequest){
        customerService.createCustomer(createCustomerRequest.email(), createCustomerRequest.name());
        return "redirect:/customers";
    }
}
