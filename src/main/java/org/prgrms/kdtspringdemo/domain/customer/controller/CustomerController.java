package org.prgrms.kdtspringdemo.domain.customer.controller;

import org.prgrms.kdtspringdemo.domain.customer.data.Customer;
import org.prgrms.kdtspringdemo.domain.customer.data.CustomerDto;
import org.prgrms.kdtspringdemo.domain.customer.service.CustomerService;
import org.prgrms.kdtspringdemo.domain.voucher.data.Voucher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/list")
    public String customerList (Model model){
        List<Customer> customers = customerService.findAllCustomer();
        model.addAttribute("customers", customers);
        return "/customer/customer-list";
    }

    @GetMapping("/{customerId}")
    public String customerDetail(@PathVariable UUID customerId,Model model){
        Customer customer = customerService.findById(customerId).get();
        model.addAttribute("customer", customer);

        return "/customer/customer-detail";
    }

    @GetMapping("/delete")
    public String allCustomerDelete(){
        customerService.deleteAll();

        return "/customer/customer-list";
    }

    @GetMapping("/newcustomer")
    public String newCustomer(Model model){
        model.addAttribute("customerDto", new CustomerDto(null,null));
        return "/customer/new-customer";
    }

    @PostMapping("/newcustomer")
    public String newCustomerPost(@ModelAttribute CustomerDto customerDto){
        Customer customer = new Customer(UUID.randomUUID(), customerDto.name(), customerDto.email(), LocalDateTime.now());
        customerService.insert(customer);
        return "redirect:/";
    }
}
