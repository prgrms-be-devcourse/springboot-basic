package com.programmers.part1.customer;

import com.programmers.part1.customer.CustomerService;
import com.programmers.part1.domain.customer.RequestCustomerDto;
import com.programmers.part1.domain.customer.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping()
    public String viewCustomerList(Model model){

        List<Customer> customerList = customerService.getAllCustomer();
        model.addAttribute("customers", customerList);
        return "customer/customers";
    }

    @GetMapping("/new")
    public String viewAddForm(){
        return "customer-add";
    }

    @PostMapping("/new")
    public String addCustomer(RequestCustomerDto requestCustomerDto){
        Customer savedCustomer = customerService.createCustomer(requestCustomerDto.getName(), requestCustomerDto.getEmail());

        return "redirect:/customers";
    }

    @GetMapping("/{customerId}")
    public String viewCustomer(@PathVariable UUID customerId, Model model){
        model.addAttribute("customer",customerService.getCustomer(customerId));
        return "/customer/customer-detail";
    }

    @PostMapping("/{customerId}")
    public String deleteCustomer(@PathVariable UUID customerId){
        customerService.deleteCustomer(customerId);
        return "redirect:/customers";
    }

    @GetMapping("/{customerId}/edit")
    public String viewCustomerEdit(@PathVariable UUID customerId, Customer customer, Model model){
        model.addAttribute(customer);
        return "/customer/customer-edit";
    }

    @PostMapping("/{customerId}/edit")
    public String editCustomerDetail(@PathVariable UUID customerId, @ModelAttribute RequestCustomerDto requestCustomerDto){
        customerService.updateCustomer(customerId, requestCustomerDto);
        return "redirect:/customers/{customerId}";
    }

}
