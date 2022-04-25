package org.prgrms.kdt.domain.customer.controller;

import org.prgrms.kdt.domain.customer.dto.CustomerCreateRequest;
import org.prgrms.kdt.domain.customer.dto.CustomerUpdateRequest;
import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.model.CustomerType;
import org.prgrms.kdt.domain.customer.service.CustomerService;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String customerList(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        model.addAttribute("voucherType", VoucherType.values());
        return "customers/list";
    }

    @GetMapping("/search")
    public String CustomerListOwnSpecificVoucher(Model model,
            @RequestParam("voucherType") VoucherType voucherType,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<Customer> customers = customerService.getCustomersByVoucherTypeAndDate(voucherType, date);
        model.addAttribute("customers", customers);
        model.addAttribute("voucherType", VoucherType.values());
        return "customers/list";
    }

    @GetMapping("/{customerId}")
    public String customerDetails(Model model, @PathVariable UUID customerId) {
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        customer.ifPresent(value -> model.addAttribute("customer", value));
        model.addAttribute("customerType", CustomerType.values());
        return "customers/detail";
    }

    @GetMapping("/new")
    public String customerCreateShow(Model model) {
        model.addAttribute("customerType", CustomerType.values());
        return "customers/create";
    }

    @PostMapping("/new")
    public String customerCreate(@Valid CustomerCreateRequest createRequest) {
        customerService.save(createRequest);
        return "redirect:/customers";
    }

    @PutMapping("/{customerId}")
    public String customerModify(@Valid CustomerUpdateRequest updateRequest,
                                 @PathVariable("customerId") UUID customerId) {
        customerService.update(updateRequest, customerId);
        return "redirect:/customers";
    }

    @DeleteMapping("/{customerId}")
    public String customerRemove(@PathVariable("customerId") UUID customerId) {
        customerService.remove(customerId);
        return "redirect:/customers";
    }

}
