package org.prgrms.springbasic.controller.view;

import lombok.RequiredArgsConstructor;
import org.prgrms.springbasic.controller.view.dto.CustomerDto;
import org.prgrms.springbasic.domain.customer.Customer;
import org.prgrms.springbasic.service.web.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.prgrms.springbasic.controller.view.dto.CustomerDto.updateCustomerDtoFrom;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public String customerList(Model model) {
        var customers = customerService.findCustomers();

        model.addAttribute("customers", customers);

        return "/customer/customers";
    }

    @GetMapping("/{customerId}/detail")
    public String customerDetails(@PathVariable UUID customerId, Model model) {
        Customer retrievedCustomer = customerService.findCustomerByCustomerId(customerId);

        model.addAttribute("customer", retrievedCustomer);

        return "/customer/customerDetail";
    }

    @GetMapping("/new")
    public String customerAdd(Model model) {
        model.addAttribute("newCustomerDto", new CustomerDto());

        return "/customer/customerAddForm";
    }

    @PostMapping("/new")
    public String customerAdd(@ModelAttribute CustomerDto customerDto) {
        var customerType = customerDto.getCustomerType();
        var customer = customerType.createCustomer(randomUUID(), customerDto.getName());

        customerService.addCustomer(customer);

        return "redirect:/customers";
    }

    @GetMapping("/{customerId}/renewal")
    public String customerModify(@PathVariable UUID customerId, Model model)  {
        Customer retrievedCustomer = customerService.findCustomerByCustomerId(customerId);

        model.addAttribute("updateCustomerDto", updateCustomerDtoFrom(retrievedCustomer));

        return "/customer/customerModifyForm";
    }

    @PostMapping("/{customerId}/renewal")
    public String customerModify(@PathVariable UUID customerId, @ModelAttribute CustomerDto updateCustomerDto) {
        Customer retrievedCustomer = customerService.findCustomerByCustomerId(customerId);

        customerService.modifyCustomer(retrievedCustomer, updateCustomerDto);


        return "redirect:/customers";
    }


    @PostMapping("/{customerId}/removal")
    public String customerRemove(@PathVariable UUID customerId) {
        customerService.removeCustomerById(customerId);

        return "redirect:/customers";
    }

    @PostMapping("/removal")
    public String customerRemoveAll() {
        customerService.removeCustomers();

        return "redirect:/customers";
    }
}
