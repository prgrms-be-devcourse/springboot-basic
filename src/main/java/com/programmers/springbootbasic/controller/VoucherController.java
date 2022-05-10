package com.programmers.springbootbasic.controller;

import com.programmers.springbootbasic.controller.dto.AllocateVoucherRequest;
import com.programmers.springbootbasic.controller.dto.Benefit;
import com.programmers.springbootbasic.domain.Voucher;
import com.programmers.springbootbasic.service.CustomerVoucherLogService;
import com.programmers.springbootbasic.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
public class VoucherController {

    private final VoucherService voucherService;
    private final CustomerVoucherLogService customerVoucherLogService;

    @Autowired
    public VoucherController(VoucherService voucherService, CustomerVoucherLogService customerVoucherLogService) {
        this.voucherService = voucherService;
        this.customerVoucherLogService = customerVoucherLogService;
    }

    @GetMapping
    public String showAllVouchers(Model model) {
        List<Voucher> allVouchers = voucherService.getAvailableVouchers();

        model.addAttribute("vouchers", allVouchers);

        return "/vouchers/voucher-main";
    }

    @GetMapping("/fixed")
    public String showNewFixedAmountVoucherPage() {
        return "/vouchers/voucher-new-fixed";
    }

    @PostMapping("/fixed")
    public String saveNewFixedAmountVoucher(Benefit benefit) {
        voucherService.createVoucher(benefit);

        return "redirect:/vouchers";
    }

    @GetMapping("/percent")
    public String showNewPercentDiscountVoucherPage() {
        return "vouchers/voucher-new-percent";
    }

    @PostMapping("/percent")
    public String saveNewPercentDiscountVoucher(Benefit benefit) {
        voucherService.createVoucher(benefit);

        return "redirect:/vouchers";
    }

    @GetMapping("/allocate/{id}")
    public String showAllocatePage(@PathVariable("id") String voucherId, Model model) {
        model.addAttribute("voucherId", voucherId);

        return "vouchers/voucher-allocate";
    }

    @PostMapping("/allocate")
    public String saveAllocateVoucherToCustomer(AllocateVoucherRequest request) {
        customerVoucherLogService.createCustomerVoucherLog(request.getCustomerId(), UUID.fromString(request.getVoucherId()));

        return "redirect:/vouchers";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") String voucherId) {
        voucherService.deleteVoucherById(UUID.fromString(voucherId));

        return "redirect:/vouchers";
    }

}
