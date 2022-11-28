package com.programmers.customer.controller;

import com.programmers.customer.controller.dto.CreateCustomerRequest;
import com.programmers.customer.controller.dto.CreateVoucherRequest;
import com.programmers.customer.domain.Customer;
import com.programmers.customer.service.CustomerService;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class CustomerController {
    private final CustomerService customerService;
    private final VoucherService voucherService;

    public CustomerController(CustomerService customerService, VoucherService voucherService) {
        this.customerService = customerService;
        this.voucherService = voucherService;
    }

    @GetMapping("/customers")
    public String viewCustomerPage(Model model) {
        var allCustomers = customerService.getAllCustomers();
        model.addAttribute("customers", allCustomers);
        return "customers";
    }

    @GetMapping("/customers/{customerEmail}/details")
    public String viewCustomerDetailPage(@PathVariable("customerEmail") String email, Model model) {
        var vouchers = voucherService.getVouchersByCustomerEmail(email);
        model.addAttribute("vouchers", vouchers);
        model.addAttribute("customerEmail", email);
        return "customer-details";
    }

    @GetMapping("/customers/{customerEmail}/details/new")
    public String createVoucherPage(@PathVariable("customerEmail") String email, Model model) {
        model.addAttribute("customerEmail", email);
        return "voucher-create";
    }

    @PostMapping("/customers/{customerEmail}/details/new")
    public String addNewVoucher(@PathVariable("customerEmail") String email, CreateVoucherRequest createVoucherRequest) {
        voucherService.createVoucher(email, createVoucherRequest.getType(), createVoucherRequest.getDiscount());
        return "redirect:/customers/" + email + "/details";
    }

    @GetMapping("/customers/{customerEmail}/{voucherId}")
    public String deleteVoucher(@PathVariable("customerEmail") String email, @PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteById(voucherId);
        return "redirect:/customers/" + email + "/details";
    }


    @GetMapping("/customers/new")
    public String viewCreateCustomerPage() {
        return "customer-create";
    }

    @PostMapping("/customers/new")
    public String createCustomer(CreateCustomerRequest createCustomerRequest) {
        customerService.createCustomer(createCustomerRequest.getEmail(), createCustomerRequest.getPassword(), createCustomerRequest.getName());
        return "redirect:/customers";
    }
    // ----- 밑으로 REST API ----- //

    @GetMapping("/api/v1/customers")
    @ResponseBody
    public List<Customer> findCustomers() {

        return customerService.getAllCustomers();
    }

    @GetMapping("/api/v1/customers/{customerEmail}")
    @ResponseBody
    public List<Voucher> findVouchersByCustomer(@PathVariable("customerEmail") String email) {
        return voucherService.getVouchersByCustomerEmail(email);
    }

    @PostMapping("/api/v1/customers/{customerEmail}")
    @ResponseBody
    public CreateVoucherRequest createVoucher(@PathVariable("customerEmail") String email, @RequestBody CreateVoucherRequest createVoucherRequest) {
        voucherService.createVoucher(email, createVoucherRequest.getType(), createVoucherRequest.getDiscount());
        return createVoucherRequest;
    }

    @GetMapping("/api/v1/{customerEmail}/{voucherId}")
    @ResponseBody
    public String deleteVoucher2(@PathVariable("customerEmail") String email, @PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteById(voucherId);
        return email;
    }

    @GetMapping("/api/v1/{customerEmail}")
    @ResponseBody
    public List<Voucher> findVouchersByDate(@PathVariable("customerEmail") String email, @RequestParam("date") String date) {
        System.out.println(date);
        return voucherService.getVouchersByDate(email, date);
    }
}
