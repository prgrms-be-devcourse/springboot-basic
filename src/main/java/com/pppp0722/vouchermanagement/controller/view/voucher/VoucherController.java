package com.pppp0722.vouchermanagement.controller.view.voucher;

import com.pppp0722.vouchermanagement.controller.dto.CreateVoucherRequest;
import com.pppp0722.vouchermanagement.controller.dto.DeleteVoucherRequest;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.service.VoucherService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(
        VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/voucher")
    public String findAllPage(Model model) {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        model.addAttribute("vouchers", vouchers);
        return "voucher/list";
    }

    @GetMapping("/voucher/{voucherId}")
    public String detailPage(@PathVariable("voucherId") UUID voucherId, Model model) {
        Voucher voucher = voucherService.getVoucherById(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher/detail";
    }

    @GetMapping("/voucher/create")
    public String createPage() {
        return "voucher/create";
    }

    @PostMapping("/voucher/create")
    public String createPage(CreateVoucherRequest createVoucherRequest) {
        voucherService.createVoucher(UUID.randomUUID(), createVoucherRequest.type(),
            createVoucherRequest.amount(), LocalDateTime.now(),
            createVoucherRequest.memberId());
        return "redirect:/voucher/create";
    }

    @GetMapping("/voucher/delete")
    public String deletePage() {
        return "voucher/delete";
    }

    @PostMapping("/voucher/delete")
    public String deletePage(DeleteVoucherRequest deleteVoucherRequest) {
        voucherService.deleteVoucher(deleteVoucherRequest.voucherId());
        return "redirect:/voucher/delete";
    }
}
