package org.prgrms.vouchermanager.handler.customer;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.domain.customer.Customer;
import org.prgrms.vouchermanager.domain.customer.CustomerRequest;
import org.prgrms.vouchermanager.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebCustomerController {
    private final CustomerService service;

    @GetMapping("/customers")
    public String findAll(Model model) {
        List<Customer> customers = service.findAll();
        model.addAttribute("customers", customers);
        return "customer/customer-list";
    }

    @GetMapping("/customers/create")
    public String createForm(){
        return "customer/create-customer";
    }
    @PostMapping("/customers/create")
    public String create(@ModelAttribute CustomerRequest customerRequest){
        service.createCustomer(customerRequest);
        return "redirect:/customers";
    }
}
