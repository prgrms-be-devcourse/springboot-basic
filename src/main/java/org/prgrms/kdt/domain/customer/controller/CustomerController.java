package org.prgrms.kdt.domain.customer.controller;

import org.prgrms.kdt.domain.customer.request.CustomerCreateRequest;
import org.prgrms.kdt.domain.customer.request.CustomerUpdateRequest;
import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.model.CustomerType;
import org.prgrms.kdt.domain.customer.service.CustomerService;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String getAllCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        model.addAttribute("voucherType", VoucherType.values());
        return "customers/list";
    }

    @GetMapping("/search")
    public String getCustomersOwnSpecificVoucher(
            Model model,
            @RequestParam("voucherType") VoucherType voucherType,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<Customer> customers = customerService.getCustomersByVoucherTypeAndDate(voucherType, date);
        model.addAttribute("customers", customers);
        model.addAttribute("voucherType", VoucherType.values());
        return "customers/list";
    }

    @GetMapping("/{customerId}")
    public String getCustomer(Model model, @PathVariable UUID customerId) {
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        customer.ifPresent(value -> model.addAttribute("customer", value));
        model.addAttribute("customerType", CustomerType.values());
        model.addAttribute("updateForm", new CustomerUpdateRequest());
        return "customers/detail";
    }

    @GetMapping("/new")
    public String moveCustomerCreatePage(Model model) {
        model.addAttribute("customerType", CustomerType.values());
        model.addAttribute("createForm", new CustomerCreateRequest());
        return "customers/create";
    }

    @PostMapping("/new")
    public String createCustomer(
            @ModelAttribute("createForm") @Valid CustomerCreateRequest createRequest,
            BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("customerType", CustomerType.values());
            return "/customers/create";
        }
        Customer customer = createRequest.toEntity();
        customerService.save(customer);
        return "redirect:/customers";
    }

    @PutMapping("/{customerId}")
    public String modifyCustomer(@Valid CustomerUpdateRequest updateRequest,
                                 @PathVariable("customerId") UUID customerId) {
        customerService.update(customerId,
                updateRequest.getName(),
                updateRequest.getEmail(),
                updateRequest.getCustomerType());
        return "redirect:/customers";
    }

    @DeleteMapping("/{customerId}")
    public String removeCustomer(@PathVariable("customerId") UUID customerId) {
        customerService.remove(customerId);
        return "redirect:/customers";
    }

}
