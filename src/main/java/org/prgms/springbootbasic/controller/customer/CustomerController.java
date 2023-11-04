package org.prgms.springbootbasic.controller.customer;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.domain.customer.CustomerRequestDto;
import org.prgms.springbootbasic.exception.EntityNotFoundException;
import org.prgms.springbootbasic.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/customers")
@Slf4j
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customerId}")
    public String detail(@PathVariable("customerId") String customerId, Model model){
        UUID customerUUID = UUID.fromString(customerId);

        Customer customer = customerService.findById(customerUUID)
                .orElseThrow(EntityNotFoundException::new);
        model.addAttribute("customer", customer);

        return "customer/customer-details";
    }

    @PostMapping("/{customerId}")
    public String update(@PathVariable String customerId, CustomerRequestDto requestDto){
        UUID customerUUID = UUID.fromString(customerId);
        String name = requestDto.name();
        String isBlackedStr = requestDto.isBlacked();
        boolean isBlacked = Objects.requireNonNull(isBlackedStr).equals("true");

        log.info("customerId = {}, name = {}, isBlacked = {}", customerId, name, isBlacked);

        customerService.update(customerUUID, name, isBlacked);

        return "redirect:/";
    }
}
