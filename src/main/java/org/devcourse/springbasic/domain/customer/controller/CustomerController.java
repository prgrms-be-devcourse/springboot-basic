package org.devcourse.springbasic.domain.customer.controller;

import lombok.RequiredArgsConstructor;
import org.devcourse.springbasic.domain.customer.dto.CustomerDto;
import org.devcourse.springbasic.domain.customer.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping()
    public String findByCriteria(
            @ModelAttribute CustomerDto.Request request,
            Model model
    ) {
        List<CustomerDto.Response> customers = customerService.findByCriteria(request);
        model.addAttribute("customers", customers);
        return "customer-list";
    }

    @GetMapping("/create")
    public String createVoucherForm() {
        return "customer-create-form";
    }

    @PostMapping()
    public String createNewVoucher(
            @Valid CustomerDto.SaveRequest request
    ) {
        customerService.save(request);
        return "redirect:/customers";
    }

    @GetMapping("/{customerId}")
    public String findById(
            @PathVariable UUID customerId,
            Model model
    ) {
        CustomerDto.Response response = customerService.findById(customerId);
        model.addAttribute("customer", response);
        return "customer-details";
    }

    @DeleteMapping("/{customerId}")
    public String deleteById(
            @PathVariable UUID customerId
    ) {
        customerService.deleteById(customerId);
        return "redirect:/customers";
    }
}