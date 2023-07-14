package com.programmers.springmission.customer.presentation;

import com.programmers.springmission.customer.application.CustomerService;
import com.programmers.springmission.customer.presentation.request.CustomerCreateRequest;
import com.programmers.springmission.customer.presentation.request.CustomerUpdateRequest;
import com.programmers.springmission.customer.presentation.response.CustomerResponse;
import com.programmers.springmission.customer.presentation.response.WalletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/customer")
public class CustomerViewController {

    private static final String REDIRECT_CUSTOMER = "redirect:/customer";

    private final CustomerService customerService;

    public CustomerViewController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    public String viewCustomerPage(Model model) {
        List<CustomerResponse> allCustomer = customerService.findAllCustomer();
        model.addAttribute("serverTime", LocalDateTime.now());
        model.addAttribute("customers", allCustomer);
        return "customer/customer";
    }

    @GetMapping("/{customerId}")
    public String findCustomer(@PathVariable("customerId") UUID customerId, Model model) {
        CustomerResponse customerResponse = customerService.findByIdCustomer(customerId);
        List<WalletResponse> walletResponses = customerService.findWallet(customerId);

        if (customerResponse != null) {
            model.addAttribute("customer", customerResponse);
            model.addAttribute("wallet", walletResponses);
            return "customer/customer-details";
        } else {
            return "global/error";
        }
    }

    @PostMapping("/{customerId}")
    public String deleteCustomer(@PathVariable("customerId") UUID customerId) {
        customerService.deleteCustomer(customerId);
        return REDIRECT_CUSTOMER;
    }

    @GetMapping("/viewNewPage")
    public String viewNewPage() {
        return "customer/customer-new";
    }

    @PostMapping("/viewNewPage")
    public String addNewCustomer(@ModelAttribute("customer") CustomerCreateRequest customerCreateRequest) {
        customerService.createCustomer(customerCreateRequest);
        return REDIRECT_CUSTOMER;
    }

    @GetMapping("/viewModifyPage/{customerId}")
    public String viewModifyPage(@PathVariable("customerId") UUID customerId, Model model) {
        CustomerResponse customerResponse = customerService.findByIdCustomer(customerId);

        if (customerResponse != null) {
            model.addAttribute("customer", customerResponse);
            return "customer/customer-modify";
        } else {
            return "global/error";
        }
    }

    @PostMapping("/viewModifyPage/name/{customerId}")
    public String modifyName(@PathVariable("customerId") UUID customerId,
                             @ModelAttribute("customer") CustomerUpdateRequest customerUpdateRequest) {
        customerService.updateName(customerId, customerUpdateRequest);
        return REDIRECT_CUSTOMER;
    }
}
