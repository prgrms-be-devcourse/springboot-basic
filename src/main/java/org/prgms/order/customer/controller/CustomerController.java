package org.prgms.order.customer.controller;

import org.prgms.order.customer.entity.Customer;
import org.prgms.order.customer.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
//@CrossOrigin(origins = "*")
public class CustomerController {

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/api/v1/customers")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public List<Customer> findCustomers(){
        return customerService.findAllCustomer();
    }

    @GetMapping("/api/v1/customers/{customerId}")
    @ResponseBody
    public ResponseEntity<Customer> findCustomer(@PathVariable("customerId") UUID customerId){
        var customer = customerService.findById(customerId);
        return customer.map(ResponseEntity::ok).orElse(ResponseEntity.status(404).build());
    }

    @PostMapping("/api/v1/customers/{customerId}")
    @ResponseBody
    public Customer saveCustomer(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDto customer){
        logger.info("Got customer save request {}",customer);
        return null;
    }



    @GetMapping("/customers")
    public String viewCustomerspage(Model model){
        var allCustomers = customerService.findAllCustomer();
        model.addAttribute("serverTime",LocalDateTime.now());
        model.addAttribute("customers",allCustomers);
        return "views/customers";
    }


    @GetMapping("/customers/new")
    public String viewNewCustomerPage(){
        return "views/new-customers";
    }

    @PostMapping("/customers/new")
    public String addNewCustomer(CreateCustomerRequest createCustomerRequest){
        customerService.createCustomer(createCustomerRequest.email(), createCustomerRequest.name());
        return "redirect:/customers";
    }


}
