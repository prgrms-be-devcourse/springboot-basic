package com.prgrms.management.voucher.controller;

import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.service.CustomerService;
import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.dto.VoucherRequest;
import com.prgrms.management.voucher.domain.VoucherType;
import com.prgrms.management.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class voucherController {
    private final CustomerService customerService;
    private final VoucherService voucherService;

    @ModelAttribute("voucherTypes")
    public VoucherType[] voucherTypes() {
        return VoucherType.values();
    }

    @GetMapping
    public String todayCreatedVouchersInfo(Model model) {
        model.addAttribute("vouchers", voucherService.findAllByVoucherTypeOrCreatedAt(null, LocalDate.now()));
        return "index";
    }

    @GetMapping("/voucher/list")
    public String voucherInfo(Model model) {
        model.addAttribute("vouchers", voucherService.findAll());
        return "voucher/voucherList";
    }

    @GetMapping("/voucher/create")
    public String createVoucherForm(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        model.addAttribute("voucherRequest", new VoucherRequest());
        return "voucher/create";
    }

    @PostMapping("/voucher/createVoucher")
    public String createVoucher(VoucherRequest voucherRequest) {
        voucherService.createVoucher(voucherRequest);
        return "redirect:/voucher/list";
    }

    @GetMapping("/voucher/search")
    public String searchVoucher() {
        return "voucher/search";
    }

    @PostMapping("/voucher/search")
    public String searchVoucher(
            @RequestParam(value = "voucherType", required = false) VoucherType voucherType,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(value = "createdAt", required = false) LocalDate createdAt,
            Model model
    ) {
        model.addAttribute("msg", "HELLO");
        model.addAttribute("vouchers", voucherService.findAllByVoucherTypeOrCreatedAt(voucherType, createdAt));
        return "voucher/searchResult";
    }

    @GetMapping("/voucher/{voucherId}")
    public String searchVoucher(@PathVariable UUID voucherId, Model model) {
        Voucher voucher = voucherService.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher/voucherInfo";
    }

    @DeleteMapping("/voucher/{voucherId}")
    public String deleteVoucher(@PathVariable String voucherId) {
        voucherService.deleteById(UUID.fromString(voucherId));
        return "redirect:/voucher/list";
    }
}
