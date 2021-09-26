package org.prgms.w3d1.controller;

import org.prgms.w3d1.controller.api.CreateCustomerRequest;
import org.prgms.w3d1.service.CustomerService;
import org.prgms.w3d1.controller.api.UpdateCustomerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/customers")
public class ViewCustomerController {

    private final CustomerService customerService;
    private static final Logger logger = LoggerFactory.getLogger(ViewCustomerController.class);

    public ViewCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public String viewCustomersPage(Model model) {
        var allCustomers = customerService.findAll();
        model.addAttribute("customers", allCustomers);
        return "views/customers";
    }

    @GetMapping("/new")
    public String viewNewCustomerPage() {
        return "views/new-customers";
    }

    @PostMapping("/new")
    public String createNewCustomer(CreateCustomerRequest createCustomerRequest) {
        customerService.createCustomer(createCustomerRequest.name(), createCustomerRequest.email());
        return "redirect:/customers";
    }

    @GetMapping("/{customerId}")
    public String viewCustomerDetail(@PathVariable("customerId") UUID customerId, Model model) {
        var maybeCustomer = customerService.getCustomer(customerId);
        if (maybeCustomer.isPresent()) {
            model.addAttribute("customer", maybeCustomer.get());
            return "views/customer-details";
        } else {
            return "views/404";
        }
    }

    @GetMapping("/update")
    public String viewCustomerUpdate(@RequestParam(value = "id") UUID customerId, Model model) {
        var maybeCustomer = customerService.getCustomer(customerId);
        if (maybeCustomer.isPresent()) {
            model.addAttribute("customer", maybeCustomer.get());
            return "views/update-customer";
        } else {
            return "views/404";
        }
    }

    @PostMapping("/update")
    public String updateCustomer(
        @RequestParam(value = "id") UUID customerId,
        UpdateCustomerRequest updateCustomerRequest, Model model)
    {
        var updatedCustomer = customerService.updateWithNameAndEmail(
            customerId, updateCustomerRequest.name(), updateCustomerRequest.email());
        model.addAttribute("customer", updatedCustomer);
        return "views/customer-details";
    }

    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam(value = "id") UUID customerId) {
        customerService.deleteCustomer(customerId);

        return "redirect:/customers";
    }
}
