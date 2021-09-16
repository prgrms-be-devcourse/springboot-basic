package org.prgrms.kdt.controller.view;

import org.prgrms.kdt.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class CustomerViewController {

    private final CustomerService customerService;

    public CustomerViewController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String list(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "views/customers";
    }

    @GetMapping("/customers/new")
    public String createForm(Model model){
        model.addAttribute("customerForm", new CustomerForm());
        return "views/new-customers";
    }

    @PostMapping("/customers/new")
    public String createForm(@Valid CustomerForm customerForm, BindingResult result){
        if(result.hasErrors()) {
            return "views/new-customers";
        }

        customerService.createCustomer(customerForm.getName(), customerForm.getEmail());
        return "redirect:/customers";
    }

}
