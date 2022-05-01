package org.prgrms.voucherapplication.controller;

import org.prgrms.voucherapplication.dto.CreateCustomerRequest;
import org.prgrms.voucherapplication.entity.Customer;
import org.prgrms.voucherapplication.entity.SqlVoucher;
import org.prgrms.voucherapplication.service.JdbcCustomerService;
import org.prgrms.voucherapplication.service.JdbcVoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class CustomerController {

    private final JdbcCustomerService customerService;
    private final JdbcVoucherService voucherService;

    public CustomerController(JdbcCustomerService customerService, JdbcVoucherService voucherService) {
        this.customerService = customerService;
        this.voucherService = voucherService;
    }

    // 고객 관리 페이지
    @GetMapping("/customers")
    public String customerManagementPage(Model model) {
        List<Customer> allCustomer = customerService.getAllCustomer();
        model.addAttribute("customers", allCustomer);
        return "customer/customers";
    }

    // 새 고객 추가하기 페이지
    @GetMapping("/customers/new")
    public String newCustomerPage() {
        return "customer/new-customer";
    }

    // 새 고객 추가
    @PostMapping("/customers/new")
    public String addNewCustomer(CreateCustomerRequest request) {
        customerService.saveCustomer(request.name(), request.email());
        return "redirect:/customers";
    }

    // 고객 상세 페이지
    @GetMapping("/customers/{customerId}")
    public String findCustomer(@PathVariable("customerId") UUID customerId, Model model) {
        Optional<Customer> maybeCustomer = customerService.getCustomerById(customerId);
        Optional<List<SqlVoucher>> vouchersByOwnedCustomer = voucherService.getVouchersByOwnedCustomer(customerId);
        if (maybeCustomer.isPresent()) {
            model.addAttribute("customer", maybeCustomer.get());
            model.addAttribute("vouchers", vouchersByOwnedCustomer.get());
            return "customer/customer-details";
        } else {
            return "view/404";
        }
    }

    // 고객 삭제
    @PostMapping("/customers/{customerId}/delete")
    public String deleteCustomerById(@PathVariable("customerId") UUID customerId) {
        customerService.deleteCustomerById(customerId);
        return "redirect:/customers";
    }

    // 고객이 보유한 바우처 전체 삭제
    @PostMapping("/customers/{customerId}/delete-voucher")
    public String deleteVouchers(@PathVariable("customerId") UUID customerId) {
        voucherService.deleteVouchersByOwnedCustomer(customerId);
        return MessageFormat.format("redirect:/customers/{0}", customerId);
    }

}
