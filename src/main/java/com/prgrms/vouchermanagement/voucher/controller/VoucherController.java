package com.prgrms.vouchermanagement.voucher.controller;

import com.prgrms.vouchermanagement.customer.Customer;
import com.prgrms.vouchermanagement.customer.CustomerDto;
import com.prgrms.vouchermanagement.customer.CustomerService;
import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
public class VoucherController {

    private final VoucherService voucherService;
    private final CustomerService customerService;

    public VoucherController(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @GetMapping
    public String vouchers(Model model) {
        List<Voucher> vouchers = voucherService.findAllVouchers();
        model.addAttribute("vouchers", VoucherDto.fromList(vouchers));
        return "voucher/vouchers";
    }

    @GetMapping("/{voucherId}")
    public String findVoucher(@PathVariable UUID voucherId, Model model) {
        Optional<Voucher> findVoucher = voucherService.findVoucherById(voucherId);
        List<Customer> customers = customerService.findCustomerByVoucher(voucherId);

        if (findVoucher.isEmpty()) {
            return "error/404";
        }

        VoucherDto voucherDto = VoucherDto.from(findVoucher.get());
        model.addAttribute("voucherTypes", VoucherType.values());
        model.addAttribute("voucher", voucherDto);
        model.addAttribute("customers", CustomerDto.fromList(customers));
        return "voucher/voucher";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("voucher", VoucherDto.getEmptyVoucherDto());
        model.addAttribute("voucherTypes", VoucherType.values());
        return "voucher/addForm";
    }

    @PostMapping("/add")
    public String addVoucher(@ModelAttribute VoucherDto voucherDto, RedirectAttributes redirectAttributes) {
        UUID voucherId = voucherService.addVoucher(voucherDto.getVoucherType(), voucherDto.getAmount());
        redirectAttributes.addAttribute("voucherId", voucherId);
        redirectAttributes.addAttribute("isSave", true);
        return "redirect:/vouchers/{voucherId}";
    }

    @PostMapping("/{voucherId}/remove")
    public String removeVoucher(@PathVariable UUID voucherId) {
        if (validateRegisteredVoucher(voucherId)) {
            return "error/404";
        }

        voucherService.removeVoucher(voucherId);
        return "redirect:/vouchers";
    }

    private boolean validateRegisteredVoucher(UUID voucherId) {
        return !voucherService.isRegisteredVoucher(voucherId);
    }
}
