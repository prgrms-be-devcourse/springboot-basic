package org.prgms.kdt.application.customer.controller.view;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.prgms.kdt.application.customer.controller.dto.CustomerRequestDto;
import org.prgms.kdt.application.customer.controller.dto.CustomerResponseDto;
import org.prgms.kdt.application.customer.domain.Customer;
import org.prgms.kdt.application.customer.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customer")
    public String customerPage(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerResponseDto> customerResponseDtoList = customers.stream()
            .map(customer -> new CustomerResponseDto(customer))
            .collect(Collectors.toList());
        model.addAttribute("customers", customerResponseDtoList);
        return "customer/customer-list";
    }

    @GetMapping("/customer/create")
    public String newCustomerPage() {
        return "customer/customer-create";
    }

    @PostMapping("/customer")
    public String newCustomer(CustomerRequestDto customerRequestDto) {
        customerService.join(customerRequestDto.getCustomer());
        return "redirect:/customer";
    }

    @GetMapping("/customer/{customerId}")
    public String getCustomerDetailPage(Model model, @PathVariable UUID customerId) {
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        model.addAttribute("customer", new CustomerResponseDto(customer.get()));
        return "customer/customer-detail";
    }
}
