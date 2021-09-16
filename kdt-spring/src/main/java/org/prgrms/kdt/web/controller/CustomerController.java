package org.prgrms.kdt.web.controller;

import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.wallet.Wallet;
import org.prgrms.kdt.service.CustomerService;
import org.prgrms.kdt.service.WalletService;
import org.prgrms.kdt.web.dto.customer.RequestCreateCustomerDto;
import org.prgrms.kdt.web.dto.customer.RequestUpdateCustomerDto;
import org.prgrms.kdt.web.dto.customer.ResponseCustomerDto;
import org.prgrms.kdt.web.dto.voucher.ResponseVoucherDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final WalletService walletService;

    public CustomerController(CustomerService customerService, WalletService walletService) {
        this.customerService = customerService;
        this.walletService = walletService;
    }

    @GetMapping
    public String viewCustomers(Model model) {
        List<Customer> customers = customerService.customers();
        List<ResponseCustomerDto> dtoList = mapToCustomerDtoList(customers);
        model.addAttribute("customers", dtoList);
        return "customer/customers";
    }

    @GetMapping("/new")
    public String viewNewVoucher() {
        return "customer/customer-new";
    }

    @PostMapping("/new")
    public String newCustomer(@ModelAttribute RequestCreateCustomerDto dto, Model model, RedirectAttributes redirectAttributes) {
        Customer customer = createCustomer(dto);
        model.addAttribute("customer", customer);
        redirectAttributes.addAttribute("customerId", customer.getCustomerId());
        return "redirect:/customers/{customerId}";

    }

    @GetMapping("/{customerId}")
    public String viewCustomerDetail(@PathVariable("customerId") UUID customerId, Model model) {
        Customer customer = customerService.findById(customerId);
        Wallet customerWallet = walletService.findCustomerWallet(customerId);

        List<ResponseVoucherDto> voucherDtoList = mapToVoucherDtoList(customerWallet);
        ResponseCustomerDto customerDto = mapToDto(customer);

        model.addAttribute("customer", customerDto);
        model.addAttribute("vouchers", voucherDtoList);
        return "customer/customer-detail";
    }

    @GetMapping("/{customerId}/edit")
    public String viewCustomerUpdate(@PathVariable("customerId") UUID customerId, Model model) {
        Customer customer = customerService.findById(customerId);
        Wallet customerWallet = walletService.findCustomerWallet(customerId);

        List<ResponseVoucherDto> voucherDtoList = mapToVoucherDtoList(customerWallet);
        ResponseCustomerDto customerDto = mapToDto(customer);

        model.addAttribute("customer", customerDto);
        model.addAttribute("vouchers", voucherDtoList);
        return "customer/customer-update";
    }

    @PostMapping("/{customerId}/edit")
    public String customerUpdate(@PathVariable("customerId") UUID customerId,
                                @ModelAttribute RequestUpdateCustomerDto dto,
                                Model model) {

        Customer customer = customerService.update(customerId, dto.getName());
        ResponseCustomerDto responseDto = mapToDto(customer);
        model.addAttribute("customer", responseDto);
        return "redirect:/customers/{customerId}";
    }

    @GetMapping("/{customerId}/delete")
    public String deleteVoucher(@PathVariable("customerId") UUID customerId) {
        customerService.deleteById(customerId);
        return "redirect:/customers";
    }

    @GetMapping("/{customerId}/assign")
    public String viewCustomerAssign(@PathVariable("customerId") UUID customerId, Model model) {
        List<Voucher> vouchers = walletService.findVouchersNotAssignedToCustomer(customerId);
        List<ResponseVoucherDto> dtoList = mapToVoucherDtoList(vouchers);
        model.addAttribute("customerId", customerId);
        model.addAttribute("vouchers", dtoList);
        return "customer/customer-assign";
    }

    @PostMapping("/{customerId}/vouchers/{voucherId}")
    public String assignVoucher(@PathVariable("customerId") UUID customerId, @PathVariable("voucherId") UUID voucherId) {
        walletService.assignVoucher(customerId, voucherId);
        return "redirect:/customers/{customerId}";
    }

    @PostMapping("/{customerId}/vouchers/{voucherId}/delete")
    public String deallocateVoucher(@PathVariable("customerId") UUID customerId, @PathVariable("voucherId") UUID voucherId) {
        walletService.deleteCustomerVoucher(customerId, voucherId);
        return "redirect:/customers/{customerId}";
    }

    private List<ResponseCustomerDto> mapToCustomerDtoList(List<Customer> customers) {
        return customers.stream()
                .map(customer -> mapToDto(customer))
                .collect(Collectors.toList());
    }

    private List<ResponseVoucherDto> mapToVoucherDtoList(Wallet customerWallet) {
        return customerWallet.getVoucherIds().stream()
                .map(voucherId -> walletService.findVoucher(voucherId))
                .map(voucher -> mapToDto(voucher))
                .collect(Collectors.toList());
    }

    private List<ResponseVoucherDto> mapToVoucherDtoList(List<Voucher> vouchers) {
        return vouchers.stream()
                .map(voucher -> new ResponseVoucherDto(voucher.getVoucherId(), voucher.getValue(), voucher.getType(), voucher.getCreatedAt()))
                .collect(Collectors.toList());
    }

    private ResponseCustomerDto mapToDto(Customer customer) {
        return new ResponseCustomerDto(customer.getCustomerId(), customer.getName(), customer.getEmail(), customer.getLastLoginAt(), customer.getCreatedAt());
    }

    private ResponseVoucherDto mapToDto(Voucher voucher) {
        return new ResponseVoucherDto(voucher.getVoucherId(), voucher.getValue(), voucher.getType(), voucher.getCreatedAt());
    }

    private Customer createCustomer(RequestCreateCustomerDto dto) {
        return customerService.insert(dto.getName(), dto.getEmail());
    }

}
