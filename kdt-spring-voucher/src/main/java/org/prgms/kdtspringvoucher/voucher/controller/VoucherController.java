package org.prgms.kdtspringvoucher.voucher.controller;

import org.prgms.kdtspringvoucher.voucher.domain.Voucher;
import org.prgms.kdtspringvoucher.voucher.dto.CreateVoucherRequest;
import org.prgms.kdtspringvoucher.voucher.dto.UpdateVoucherRequest;
import org.prgms.kdtspringvoucher.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("voucher")
public class VoucherController {
    private final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    //voucher main page
    @GetMapping
    public String getVoucherMain() {
        return "voucher/voucher-main";
    }

    //voucher list page
    @GetMapping("list")
    public String getVouchers(Model model) {
        List<Voucher> vouchers = voucherService.getVouchers();
        model.addAttribute("vouchers", vouchers);
        return "voucher/voucher-list";
    }

    //voucher create page
    @GetMapping("new")
    public String createNewVoucher() {
        return "voucher/voucher-new";
    }

    @PostMapping("new")
    public String createNewVoucher(CreateVoucherRequest createVoucherRequest) {
        Voucher voucher = voucherService.createVoucher(createVoucherRequest.getVoucherType(), createVoucherRequest.getAmount());
        return "redirect:/voucher/" + voucher.getVoucherId();
    }

    //voucher detail page
    @GetMapping("{voucherId}")
    public String getVoucherDetail(@PathVariable("voucherId") UUID voucherId, Model model) {
        Voucher voucher = voucherService.getVoucherById(voucherId).orElse(null);
        model.addAttribute("voucher", voucher);
        return "voucher/voucher-detail";
    }

    //voucher update page
    @GetMapping("update/{voucherId}")
    public String updateVoucher(@PathVariable("voucherId") UUID voucherId, Model model) {
        Voucher voucher = voucherService.getVoucherById(voucherId).orElse(null);
        model.addAttribute("voucher", voucher);
        return "voucher/voucher-update";
    }

    @PostMapping("update/{voucherId}")
    public String updateVoucher(@PathVariable("voucherId") UUID voucherId, UpdateVoucherRequest updateVoucherRequest) {
        voucherService.updateVoucherById(voucherId, updateVoucherRequest);
        return "redirect:/voucher/list";
    }

    //voucher delete
    @GetMapping("delete/{voucherId}")
    public String deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteVoucherById(voucherId);
        return "redirect:/voucher/list";
    }
}
