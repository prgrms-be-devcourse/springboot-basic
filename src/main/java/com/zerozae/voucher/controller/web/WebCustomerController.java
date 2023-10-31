package com.zerozae.voucher.controller.web;

import com.zerozae.voucher.dto.customer.CustomerCreateRequest;
import com.zerozae.voucher.dto.customer.CustomerResponse;
import com.zerozae.voucher.dto.customer.CustomerUpdateRequest;
import com.zerozae.voucher.service.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

import static com.zerozae.voucher.validator.InputValidator.validateInputUuid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")
public class WebCustomerController {
    
    private final CustomerService customerService;

    @GetMapping("/createForm")
    public String getCreateForm() {
        return "/customer/createForm";
    }

    @PostMapping
    public String createCustomer(@Valid @ModelAttribute CustomerCreateRequest customerCreateRequest) {
        customerService.createCustomer(customerCreateRequest);
        return "redirect:/customers";
    }

    @GetMapping
    public String findAllCustomers(Model model) {
        List<CustomerResponse> customers = customerService.findAllCustomers();
        model.addAttribute("customers", customers);
        return "/customer/customers";
    }

    @GetMapping("/{customerId}")
    public String customerDetailPage(@PathVariable("customerId") String customerId, Model model) {
        validateInputUuid(customerId);
        CustomerResponse customer = customerService.findById(UUID.fromString(customerId));
        model.addAttribute("customer", customer);
        return "/customer/customerDetail";
    }

    @GetMapping("/updateForm/{customerId}")
    public String getUpdateForm(@PathVariable("customerId") String customerId, Model model) {
        validateInputUuid(customerId);
        CustomerResponse customer = customerService.findById(UUID.fromString(customerId));
        model.addAttribute("customer", customer);
        return "customer/updateForm";
    }

    @PatchMapping("/{customerId}")
    public String updateCustomer(@PathVariable("customerId") String customerId,
                                 @Valid @ModelAttribute CustomerUpdateRequest customerUpdateRequest) {

        validateInputUuid(customerId);
        customerService.update(UUID.fromString(customerId), customerUpdateRequest);
        return "redirect:/customers";
    }

    @DeleteMapping("/{customerId}")
    public String deleteCustomer(@PathVariable("customerId") String customerId) {
        validateInputUuid(customerId);
        customerService.deleteById(UUID.fromString(customerId));
        return "redirect:/customers";
    }
}
