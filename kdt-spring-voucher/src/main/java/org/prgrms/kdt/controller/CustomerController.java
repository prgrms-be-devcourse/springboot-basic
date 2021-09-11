package org.prgrms.kdt.controller;

import org.prgrms.kdt.domain.CustomerEntity;
import org.prgrms.kdt.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    //rest api
//    @GetMapping("/customers")
//    public String viewCustomersPage(Model model){
//        List<CustomerEntity> allCustomers = customerService.getAllCustomers();
//
//        model.addAttribute("serverTime", LocalDateTime.now());
//        model.addAttribute("customers",allCustomers);
//
//        return "views/customers";
//    }

//    public String findCustomers(Model model){
    @RequestMapping(value = "/customers",method = RequestMethod.GET)
    public ModelAndView findCustomers(){
        List<CustomerEntity> allCustomers = customerService.getAllCustomers();
        // return new ModelAndView("customers", Map.of("serverTime", LocalDateTime.now()));

//        model.addAttribute("serverTime",LocalDateTime.now());
//        model.addAttribute("customers",allCustomers);

//        return "views/customers";
        return new ModelAndView("views/customers",
            Map.of("serverTime",
                    LocalDateTime.now(),
                    "customers",
                    allCustomers));
    }



}
