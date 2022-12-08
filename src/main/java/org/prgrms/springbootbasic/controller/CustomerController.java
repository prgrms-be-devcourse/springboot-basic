package org.prgrms.springbootbasic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbootbasic.dto.CustomerInputDto;
import org.prgrms.springbootbasic.dto.CustomerUpdateDto;
import org.prgrms.springbootbasic.entity.Customer;
import org.prgrms.springbootbasic.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping(value = "/home")
    public String home() {
        return "customers/home";
    }

    @GetMapping(value = "/new")
    public String createCustomer(Model model) {
        model.addAttribute("customerInput", new CustomerInputDto());
        return "customers/createPage";
    }

    @PostMapping(value = "/new")
    public String createCustomer(@Validated @ModelAttribute("customerInput") CustomerInputDto customerInput,
                                 BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (customerService.isDuplicatedEmail(customerInput.getEmail())) {
            bindingResult.reject("customer.duplicatedEmail", null, null);
        }

        if (bindingResult.hasErrors()) {
            log.error("errors={}", bindingResult);
            return "customers/createPage";
        }

        Customer customer = customerService.createCustomer(customerInput);
        redirectAttributes.addAttribute("customerId", customer.getCustomerId());
        return "redirect:/customers/{customerId}";
    }

    @GetMapping(value = "/{customerId}/edit-form")
    public String editCustomer(@PathVariable String customerId, Model model) {
        Customer customerById = customerService.lookupCustomerById(customerId);
        model.addAttribute("customer", customerById);
        return "customers/editPage";
    }

    @PatchMapping(value = "/{customerId}/edit-form")
    public String editCustomer(@PathVariable("customerId") String customerId, @Validated @ModelAttribute("customer") CustomerUpdateDto customerUpdate,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.error("errors={}", bindingResult);
            return "customers/editPage";
        }

        Customer customer = customerService.lookupCustomerById(customerId);
        customer.changeName(customerUpdate.getName());
        customerService.editCustomer(customerUpdate);

        redirectAttributes.addAttribute("customerId", customerId);
        return "redirect:/customers/{customerId}";
    }

    @GetMapping(value = "/{customerId}")
    public String lookupCustomer(@PathVariable String customerId, Model model) {
        Customer customerById = customerService.lookupCustomerById(customerId);
        model.addAttribute("customer", customerById);
        return "customers/detailPage";
    }

    @GetMapping(value = "/list")
    public String lookupCustomerList(Model model) {
        List<Customer> customerList = customerService.lookupCustomerList();
        model.addAttribute("customerList", customerList);
        return "customers/lookupPage";
    }

    @DeleteMapping(value = "/{customerId}")
    public String delete(@PathVariable("customerId") String customerId) {
        customerService.removeCustomerById(customerId);
        return "redirect:/customers/list";
    }
}
