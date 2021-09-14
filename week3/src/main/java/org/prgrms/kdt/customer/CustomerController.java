package org.prgrms.kdt.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class CustomerController {

    private final static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String viewCustomersPage(Model model){
        var allCustomers = customerService.getAllCustomer();
        model.addAttribute("customers", allCustomers);
        return "/customers";
    }

    @GetMapping("/customers/{customerId}")
    public String findCustomer(@PathVariable("customerId") UUID customerId, Model model){
        var mayBeCustomer = customerService.getCustomer(customerId);
        if(mayBeCustomer.isPresent()){
            model.addAttribute("customer", mayBeCustomer.get());
            return "/customer-details";
        }else{
            return "/404";
        }
    }

    @GetMapping("/customers/new")
    public String viewNewCustomerPage(){
        return "/new-customers";
    }

    @PostMapping (path = "/customers/new")
    public String addNewCustomer(createCustomerRequest createCustomerRequest){
        customerService.createCustomer(createCustomerRequest.email(), createCustomerRequest.name());
        return "redirect:/customers";
    }
}
