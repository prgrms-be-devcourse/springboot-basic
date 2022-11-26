package com.example.springbootbasic.controller.customer;

import com.example.springbootbasic.controller.request.CreateCustomerRequest;
import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.domain.customer.CustomerStatus;
import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.dto.customer.CustomerDto;
import com.example.springbootbasic.dto.voucher.VoucherDto;
import com.example.springbootbasic.service.customer.JdbcCustomerService;
import com.example.springbootbasic.service.voucher.JdbcVoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/view")
public class ViewCustomerController {

    private final JdbcCustomerService customerService;
    private final JdbcVoucherService voucherService;

    public ViewCustomerController(JdbcCustomerService jdbcCustomerService, JdbcVoucherService voucherService) {
        this.customerService = jdbcCustomerService;
        this.voucherService = voucherService;
    }

    @GetMapping("/v1/customers")
    public String customerList(Model model) {
        List<CustomerDto> result = customerService.findAllCustomers()
                .stream()
                .map(CustomerDto::newInstance)
                .toList();
        model.addAttribute("customers", result);
        return "customer-list";
    }

    @GetMapping("/v1/customer-add")
    public String customerAddForm() {
        return "customer-add";
    }

    @PostMapping("/v1/customer-add")
    public String customerAddForm(CreateCustomerRequest request) {
        CustomerStatus customerStatus = CustomerStatus.of(request.status());
        customerService.saveCustomer(new Customer(customerStatus));
        return "redirect:customers";
    }

    @GetMapping("/v1/customers-find")
    public String findCustomerById(@RequestParam Long customerId, Model model, RedirectAttributes re) {
        CustomerDto findCustomer = CustomerDto.newInstance(customerService.findCustomerById(customerId));
        if (findCustomer.isEmpty()) {
            re.addFlashAttribute("findResult", false);
            return "redirect:customers";
        }
        model.addAttribute("customers", findCustomer);
        return "customer-list";
    }

    @GetMapping("/v1/customer-vouchers/{customerId}")
    public String findCustomerVoucherList(@PathVariable Long customerId, Model model) {
        Customer findCustomer = customerService.findCustomerById(customerId);
        List<Voucher> findVouchers = customerService.findVouchersByCustomerId(customerId);
        findVouchers.forEach(findCustomer::receiveFrom);
        CustomerDto result = CustomerDto.newInstance(findCustomer);
        model.addAttribute("customer", result);
        return "customer-voucher-list";
    }

    @GetMapping("/v1/customer-vouchers")
    public String findCustomerDetailPage(@RequestParam Long customerId) {
        return "redirect:customer-vouchers/" + customerId;
    }

    @GetMapping("/v1/customer-vouchers-add/{customerId}")
    public String customerVouchersAddForm(@PathVariable Long customerId, Model model) {
        List<VoucherDto> findVouchers = voucherService.findAllVouchers()
                .stream()
                .map(VoucherDto::newInstance)
                .toList();
        model.addAttribute("customerId", customerId);
        model.addAttribute("vouchers", findVouchers);
        return "customer-voucher-add";
    }

    @PostMapping("/v1/customer-vouchers-add/{customerId}")
    public String customerVouchersAddForm(@PathVariable Long customerId, @RequestParam Long voucherId, RedirectAttributes re) {
        boolean isSaveSuccess = customerService.saveVoucherById(customerId, voucherId);
        re.addFlashAttribute("saveResult", isSaveSuccess);
        if (isSaveSuccess) {
            return "redirect:/view/v1/customer-vouchers/" + customerId;
        }
        return "redirect:" + customerId;
    }

    @DeleteMapping("/v1/customers/{customerId}")
    public String deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomerById(customerId);
        return "redirect:";
    }

    @DeleteMapping("/v1/customer-vouchers/{customerId}/{voucherId}")
    public String deleteCustomerVoucher(@PathVariable Long customerId, @PathVariable Long voucherId, RedirectAttributes re) {
        boolean isDeleteSuccess = customerService.deleteCustomerVoucherByIds(customerId, voucherId);
        re.addFlashAttribute("deleteResult", isDeleteSuccess);
        return "redirect:/view/v1/customer-vouchers/" + customerId;
    }
}
