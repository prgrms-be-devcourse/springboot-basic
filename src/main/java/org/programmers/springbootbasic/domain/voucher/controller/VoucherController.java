package org.programmers.springbootbasic.domain.voucher.controller;

import org.programmers.springbootbasic.domain.voucher.dto.VoucherInputDto;
import org.programmers.springbootbasic.domain.voucher.model.Voucher;
import org.programmers.springbootbasic.domain.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/manage")
public class VoucherController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/voucher")
    public String vouchersPage(Model model) {
        try {
            List<Voucher> vouchers = voucherService.findAll();
            model.addAttribute("vouchers", vouchers);
            return "voucher-list";
        } catch (RuntimeException e) {
            logger.warn(e.getMessage());
            return "500";
        }

    }

    @GetMapping("/voucher/new")
    public String newVoucherPage() {
        return "voucher-new";
    }

    @PostMapping("/voucher")
    public String newVoucher(VoucherInputDto voucherInputDto) {
        try {
            voucherService.createVoucher(voucherInputDto);
            return "redirect:/manage/voucher";
        } catch (RuntimeException e) {
            logger.warn(e.getMessage());
            return "500";
        }
    }

    @GetMapping("/voucher/{voucherId}")
    public String voucherDetailPage(@PathVariable("voucherId") UUID voucherId, Model model) {
        try {
            Voucher voucher = voucherService.findOneById(voucherId);
            model.addAttribute("voucher", voucher);
            return "voucher-detail";
        } catch (RuntimeException e) {
            logger.warn(e.getMessage());
            return "500";
        }
    }
}
