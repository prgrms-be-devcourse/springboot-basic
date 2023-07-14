package org.weekly.weekly.web.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.weekly.weekly.customer.dto.request.CustomerCreationRequest;
import org.weekly.weekly.customer.dto.request.CustomerUpdateRequest;
import org.weekly.weekly.customer.dto.response.CustomerResponse;
import org.weekly.weekly.customer.dto.response.CustomersResponse;
import org.weekly.weekly.customer.exception.CustomerException;
import org.weekly.weekly.customer.service.CustomerService;
import org.weekly.weekly.web.exception.WebExceptionDto;

@Controller
@RequestMapping("/customer")
public class CustomerWebController {
    private final Logger logger = LoggerFactory.getLogger(CustomerWebController.class);
    private final CustomerService customerService;

    public CustomerWebController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/menu")
    public String menu() {
        return "customer/menu";
    }

    @GetMapping("/findAll")
    public String findCustomers(Model model) {
        CustomersResponse customersResponse = customerService.findAllCustomer();
        model.addAttribute("customers", customersResponse);
        return "customer/findCustomers";
    }

    @GetMapping("/create")
    public String createCustomer() {
        return "customer/createCustomer";
    }

    @PostMapping("/create")
    public String createCustomer(CustomerCreationRequest creationRequest, Model model) {
        try {
            CustomerResponse customerResponse = customerService.createCustomer(creationRequest);
            model.addAttribute("customer", customerResponse);
        } catch (CustomerException exception) {
            model.addAttribute("exception", new WebExceptionDto(exception));
            return "exception/exception";
        }
        return "customer/customerInfo";
    }


    @GetMapping("/find")
    public String findCustomer() {
        return "customer/findCustomer";
    }

    @PostMapping("/find")
    public String findCustomer(CustomerUpdateRequest updateRequest, Model model) {
        try {
            CustomerResponse customerResponse = customerService.findDetailCustomer(updateRequest);
            model.addAttribute("customer", customerResponse);
        } catch (CustomerException | EmptyResultDataAccessException exception) {
            model.addAttribute("exception", new WebExceptionDto(exception));
            return "exception/exception";
        }
        return "customer/customerInfo";
    }

    @GetMapping("/delete")
    public String deleteCustomer() {
        return "customer/deleteCustomer";
    }

    @PostMapping("/delete")
    public String deleteCustomer(CustomerUpdateRequest updateRequest, Model model) {
        try {
            customerService.deleteCustomer(updateRequest);
        } catch (CustomerException | EmptyResultDataAccessException exception) {
            model.addAttribute("exception", new WebExceptionDto(exception));
            return "exception/exception";
        }
        return "redirect:/";
    }

    @GetMapping("/deleteAll")
    public String deleteAllCustomer() {
        return "customer/deleteAll";
    }

    @PostMapping("/deleteAll")
    public String deleteAllCustomers(Model model) {
        try {
            customerService.deleteAllCustomers();
        } catch (CustomerException | EmptyResultDataAccessException exception) {
            model.addAttribute("exception", new WebExceptionDto(exception));
            return "exception/exception";
        }
        return "redirect:/";
    }

    @GetMapping("/update")
    public String updateCustomer() {
        return "customer/updateCustomer";
    }

    @PostMapping("/update")
    public String updateCustomer(CustomerUpdateRequest updateRequest, Model model) {
        try {
            CustomerResponse customerResponse = customerService.updateCustomer(updateRequest);
            model.addAttribute("customer", customerResponse);
        } catch (CustomerException | EmptyResultDataAccessException exception) {
            model.addAttribute("exception", new WebExceptionDto(exception));
            return "exception/exception";
        }
        return "customer/customerInfo";
    }
}
