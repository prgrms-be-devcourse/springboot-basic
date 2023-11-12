package com.prgrms.springbasic.domain.voucher.controller;

import com.prgrms.springbasic.domain.voucher.dto.CreateVoucherRequest;
import com.prgrms.springbasic.domain.voucher.dto.UpdateVoucherRequest;
import com.prgrms.springbasic.domain.voucher.dto.VoucherResponse;
import com.prgrms.springbasic.domain.voucher.entity.DiscountType;
import com.prgrms.springbasic.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
public class VoucherWebController {

    private final VoucherService voucherService;

    public VoucherWebController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/list")
    public String voucherList(Model model) {
        List<VoucherResponse> vouchers = voucherService.findAll();
        model.addAttribute("vouchers", vouchers);
        return "voucher/list";
    }

    @GetMapping("/create-form")
    public String createVoucherForm() {
        return "voucher/create_form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute CreateVoucherRequest createVoucherRequest) {
        voucherService.saveVoucher(createVoucherRequest);
        return "redirect:/vouchers/list";
    }

    @GetMapping("/update/{voucherId}")
    public String updateForm(@PathVariable String voucherId, Model model) {
        VoucherResponse voucher = voucherService.findById(UUID.fromString(voucherId));
        model.addAttribute("voucher", voucher);
        return "voucher/update_form";
    }

    @PatchMapping("/update")
    public String update(@ModelAttribute UpdateVoucherRequest updateVoucherRequest) {
        voucherService.updateVoucher(updateVoucherRequest);
        return "redirect:/vouchers/list";
    }

    @GetMapping("/detail/{voucherId}")
    public String findVoucher(@PathVariable String voucherId, Model model) {
        VoucherResponse voucher = voucherService.findById(UUID.fromString(voucherId));
        model.addAttribute("voucher", voucher);
        return "voucher/detail";
    }

    @DeleteMapping("/delete/{voucherId}")
    public String deleteVoucher(@PathVariable String voucherId) {
        voucherService.deleteById(UUID.fromString(voucherId));
        return "redirect:/vouchers/list";
    }

    @ModelAttribute("discountType")
    private DiscountType[] discountTypes() {
        return DiscountType.values();
    }
}
