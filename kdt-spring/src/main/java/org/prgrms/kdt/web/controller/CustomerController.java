package org.prgrms.kdt.web.controller;

import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.service.CustomerService;
import org.prgrms.kdt.web.dto.customer.RequestCreateCustomerDto;
import org.prgrms.kdt.web.dto.customer.RequestUpdateCustomerDto;
import org.prgrms.kdt.web.dto.customer.ResponseCustomerDto;
import org.prgrms.kdt.web.dto.voucher.RequestUpdateVoucherDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String viewCustomers(Model model) {
        List<Customer> customers = customerService.customers();
        List<ResponseCustomerDto> dtoList = mapToDtoList(customers);
        model.addAttribute("customers", dtoList);
        return "customer/customers";
    }

    @GetMapping("/new")
    public String viewNewVoucher() {
        return "customer/customer-new";
    }

    @PostMapping("/new")
    public String newCustomer(@ModelAttribute RequestCreateCustomerDto dto, Model model, RedirectAttributes redirectAttributes) {
        Customer customer = createCustomer(dto);
        model.addAttribute("customer", customer);
        redirectAttributes.addAttribute("customerId", customer.getCustomerId());
        return "redirect:/customers/{customerId}";

    }

    @GetMapping("/{customerId}")
    public String viewCustomerDetail(@PathVariable("customerId") UUID customerId, Model model) {
        Customer customer = customerService.findById(customerId);
        ResponseCustomerDto dto = mapToDto(customer);
        model.addAttribute("customer", dto);
        return "customer/customer-detail";
    }

    @GetMapping("/{customerId}/edit")
    public String viewCustomerUpdate(@PathVariable("customerId") UUID customerId, Model model) {
        Customer customer = customerService.findById(customerId);
        ResponseCustomerDto dto = mapToDto(customer);
        model.addAttribute("customer", dto);
        return "customer/customer-update";
    }

    @PostMapping("/{customerId}/edit")
    public String customerUpdate(@PathVariable("customerId") UUID customerId,
                                @ModelAttribute RequestUpdateCustomerDto dto,
                                Model model) {

        Customer customer = customerService.update(customerId, dto.getName());
        ResponseCustomerDto responseDto = mapToDto(customer);
        model.addAttribute("customer", responseDto);
        return "redirect:/customers/{customerId}";
    }

    @GetMapping("/{customerId}/delete")
    public String deleteVoucher(@PathVariable("customerId") UUID customerId) {
        customerService.deleteById(customerId);
        return "redirect:/customers";
    }


    private List<ResponseCustomerDto> mapToDtoList(List<Customer> customers) {
        return customers.stream()
                .map(customer -> mapToDto(customer))
                .collect(Collectors.toList());
    }

    private ResponseCustomerDto mapToDto(Customer customer) {
        return new ResponseCustomerDto(customer.getCustomerId(), customer.getName(), customer.getEmail(), customer.getLastLoginAt(), customer.getCreatedAt());
    }

    private Customer createCustomer(RequestCreateCustomerDto dto) {
        return customerService.insert(dto.getName(), dto.getEmail());
    }

}
