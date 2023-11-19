package org.prgms.springbootbasic.controller.customer;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.controller.customer.dto.CustomerRequestDto;
import org.prgms.springbootbasic.exception.EntityNotFoundException;
import org.prgms.springbootbasic.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/create")
    public String showCreatePage() {
        return "customer/customer-create";
    }

    @PostMapping("/create")
    public String create(CustomerRequestDto customerRequestDto) {
        log.info(String.valueOf(customerRequestDto));

        customerService.insert(customerRequestDto.name(), customerRequestDto.email());

        return "redirect:/";
    }

    @GetMapping("/{customerId}")
    public String showDetail(@PathVariable("customerId") String customerId, Model model) {
        UUID customerUUID = UUID.fromString(customerId);

        Customer customer = customerService.findById(customerUUID)
                .orElseThrow(EntityNotFoundException::new);
        model.addAttribute("customer", customer);

        return "customer/customer-details";
    }

    @PostMapping("/{customerId}")
    public String update(@PathVariable String customerId, CustomerRequestDto requestDto) {
        UUID customerUUID = UUID.fromString(customerId);
        String name = requestDto.name();
        String isBlackedStr = requestDto.isBlacked();
        boolean isBlacked = Objects.requireNonNull(isBlackedStr).equals("true");

        log.info("customerId = {}, name = {}, isBlacked = {}", customerId, name, isBlacked);

        customerService.update(customerUUID, name, isBlacked);

        return "redirect:/";
    }

    @DeleteMapping("/{customerId}")
    public void delete(@PathVariable String customerId) {
        customerService.deleteById(UUID.fromString(customerId));
    }
}
