package org.prgrms.springbootbasic.engine.controller;

import org.prgrms.springbootbasic.engine.controller.dto.CustomerCreateRequestDto;
import org.prgrms.springbootbasic.engine.controller.dto.CustomerResponseDto;
import org.prgrms.springbootbasic.engine.controller.dto.CustomerUpdateRequestDto;
import org.prgrms.springbootbasic.engine.domain.Customer;
import org.prgrms.springbootbasic.engine.domain.Voucher;
import org.prgrms.springbootbasic.engine.service.CustomerService;
import org.prgrms.springbootbasic.exception.RecordNotFoundException;
import org.prgrms.springbootbasic.exception.VoucherException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.prgrms.springbootbasic.engine.util.UUIDUtil.convertStringToUUID;

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
    public String viewCustomerCreatePage(Model model) {
        return "views/new-customer";
    }

    @PostMapping("/customers/new")
    public String createNewCustomer(CustomerCreateRequestDto customerCreateRequestDto) {
        Customer customer = customerService.createCustomer(customerCreateRequestDto.getName(), customerCreateRequestDto.getEmail());
        return "redirect:/customers/" + customer.getCustomerId();
    }

    @GetMapping("/customers/{customerId}")
    public String viewCustomerDetailPage(Model model, @PathVariable String customerId) {
        UUID id = convertStringToUUID(customerId);
        Customer customer = customerService.getCustomerById(id);
        CustomerResponseDto customerResponseDto = new CustomerResponseDto(customer);
        customerService.getVouchersByCustomer(customer).forEach(v -> customerResponseDto.addVoucher(v.getVoucherId()));
        model.addAttribute("customer", customerResponseDto);
        return "views/customer";
    }

    @GetMapping("/customers/{customerId}/delete")
    public String deleteCustomer(@PathVariable String customerId) {
        UUID id = convertStringToUUID(customerId);
        Customer customer = customerService.getCustomerById(id);
        customerService.removeCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/customers/{customerId}/edit")
    public String showVoucherEditPage(Model model, @PathVariable String customerId) {
        UUID id = convertStringToUUID(customerId);
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "views/update-customer";
    }

    @PostMapping("/customers/update")
    public String updateCustomer(CustomerUpdateRequestDto customerUpdateRequestDto) {
        Customer customer = customerService.getCustomerById(customerUpdateRequestDto.getCustomerId());
        customer.changeName(customerUpdateRequestDto.getName());
        customerService.updateCustomer(customer);
        return "redirect:/customers/" + customer.getCustomerId();
    }

    @GetMapping("/error/error")
    public String error() {
        return "views/error";
    }

    @ExceptionHandler({RuntimeException.class})
    public String handleRuntimeException(RuntimeException ex, Model model) {
        model.addAttribute("exception", ex);
        return "views/error/400";
    }

    @ExceptionHandler({Exception.class})
    public String handleException(Exception ex, Model model) {
        model.addAttribute("exception", ex);
        return "views/error/500";
    }
}
