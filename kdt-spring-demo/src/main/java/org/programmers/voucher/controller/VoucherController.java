package org.programmers.voucher.controller;

import org.programmers.voucher.model.CreateVoucherRequest;
import org.programmers.voucher.service.VoucherJdbcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class VoucherController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    private final VoucherJdbcService voucherJdbcService;


    public VoucherController(VoucherJdbcService voucherJdbcService) {
        this.voucherJdbcService = voucherJdbcService;
    }

    @GetMapping("/vouchers")
    public String viewVouchersPage(Model model) {
        var allVouchers = voucherJdbcService.findAllVouchers();
        model.addAttribute("vouchers", allVouchers);
        return "vouchers";
    }

    @GetMapping("/vouchers/new")
    public String viewNewVoucherspage() {
        return "new-vouchers";
    }

    @PostMapping("/vouchers/new")
    public String addNewVoucher(CreateVoucherRequest createVoucherRequest) {
        voucherJdbcService.save(createVoucherRequest);
        return "redirect:/vouchers";
    }

    @GetMapping("/voucher/{voucherId}")
    public String viewFindVoucherPage(@PathVariable("voucherId") UUID voucherId, Model model) {
        var maybeVoucher = voucherJdbcService.findById(voucherId);
        model.addAttribute("voucher", maybeVoucher);
        return "voucher-detail";
    }

    @GetMapping("/voucher/delete/{voucherId}")
    public String deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherJdbcService.deleteByVoucherId(voucherId);
        return "redirect:/vouchers";
    }

    @GetMapping("/voucher/update/{voucherId}")
    public String viewUpdateVoucherPage(@PathVariable("voucherId") UUID voucherId, Model model) {
        var maybeVoucher = voucherJdbcService.findById(voucherId);
        model.addAttribute("voucher", maybeVoucher);
        return "voucher-update";
    }

    @PostMapping("/voucher/update/{voucherId}")
    public String updateVoucher(@PathVariable("voucherId") UUID voucherId, CreateVoucherRequest createVoucherRequest) {
        voucherJdbcService.update(voucherId, createVoucherRequest.getVoucherType(), createVoucherRequest.getVoucherValue());
        return "redirect:/vouchers";
    }

}
