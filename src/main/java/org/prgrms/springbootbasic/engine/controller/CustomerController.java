package org.prgrms.springbootbasic.engine.controller;

import org.prgrms.springbootbasic.engine.controller.dto.CustomerCreateRequestDto;
import org.prgrms.springbootbasic.engine.controller.dto.CustomerResponseDto;
import org.prgrms.springbootbasic.engine.domain.Customer;
import org.prgrms.springbootbasic.engine.service.CustomerService;
import org.prgrms.springbootbasic.exception.VoucherException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String viewCustomersPage(Model model) {
        List<CustomerResponseDto> allCustomers = customerService
                .getAllCustomers()
                .stream()
                .map(CustomerResponseDto::new).toList();
        model.addAttribute("customers", allCustomers);
        return "views/customers";
    }

    @GetMapping("/customers/new")
    public String viewNewCustomerPage(Model model) {
        return "views/new-customer";
    }

    @PostMapping("/customers/new")
    public String createNewCustomer(CustomerCreateRequestDto customerCreateRequestDto) {
        Customer customer = customerService.createCustomer(customerCreateRequestDto.getName(), customerCreateRequestDto.getEmail());
        return "redirect:/customers";
    }

    @GetMapping("/customers/{customerId}")
    public String viewCustomerDetailPage(Model model, @PathVariable String customerId) {
        Customer customer = findCustomerByStringId(customerId);
        CustomerResponseDto customerResponseDto = new CustomerResponseDto(customer);
        customerService.getVouchersByCustomer(customer).forEach(v -> customerResponseDto.addVoucher(v.getVoucherId()));
        model.addAttribute("customer", customerResponseDto);
        return "views/customer";
    }

    @GetMapping("/customers/{customerId}/delete")
    public String deleteCustomer(@PathVariable String customerId) {
        Customer customer = findCustomerByStringId(customerId);
        customerService.removeCustomer(customer);
        return "redirect:/customers";
    }

    private Customer findCustomerByStringId(String customerId) {
        UUID id;
        try {
            id = UUID.fromString(customerId);
        }catch (IllegalArgumentException ex) {
            throw new VoucherException("Invalid Id format");
        }
        return customerService.getCustomerById(id);
    }
}
