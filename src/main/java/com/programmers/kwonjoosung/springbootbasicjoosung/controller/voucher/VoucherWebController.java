package com.programmers.kwonjoosung.springbootbasicjoosung.controller.voucher;


import com.programmers.kwonjoosung.springbootbasicjoosung.controller.voucher.request.VoucherDto;
import com.programmers.kwonjoosung.springbootbasicjoosung.controller.voucher.request.CreateVoucherRequest;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;
import com.programmers.kwonjoosung.springbootbasicjoosung.service.VoucherService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherWebController {

    private final VoucherService voucherService;

    public VoucherWebController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/voucher/new")
    public String createVoucherPage() {
        return "/voucher/new";
    }

    @PostMapping("/voucher/new")
    public ModelAndView createVoucher(CreateVoucherRequest request) {
        try {
            voucherService.saveVoucher(request);
            return new ModelAndView("redirect:/voucher/list");
//            return new ModelAndView("redirect:/voucher/" + voucherDto.getVoucherId());
        } catch (RuntimeException e) {
            return new ModelAndView("/voucher/error", "message", e.getMessage());
        }

    }

    @GetMapping("/voucher/{voucherId}")
    public ModelAndView findVoucher(@PathVariable String voucherId) {
        try {
            VoucherDto voucher = voucherService.findVoucher(UUID.fromString(voucherId));
            return new ModelAndView("/voucher/detail", "voucher", voucher);
        } catch (RuntimeException e) {
            return new ModelAndView("/voucher/error", "message", e.getMessage());
        }
    }

    @GetMapping("/voucher/list")
    public ModelAndView listVoucher() {
        try {
            List<VoucherDto> vouchers = voucherService.getAllVouchers();
            return new ModelAndView("/voucher/list", "vouchers", vouchers);
        } catch (RuntimeException e) {
            return new ModelAndView("/voucher/error", "message", e.getMessage());
        }
    }

    @PostMapping("/voucher/{voucherId}")
    public ModelAndView updateVoucher(@PathVariable String voucherId, String voucherType, Long discount) {
        try {
            voucherService.updateVoucher(UUID.fromString(voucherId), VoucherType.valueOf(voucherType), discount);
            return new ModelAndView("redirect:/voucher/list");
        } catch (RuntimeException e) {
            return new ModelAndView("/voucher/error","message", e.getMessage());
        }
    }

    @PostMapping("/voucher/delete/{voucherId}")
    public ModelAndView deleteVoucher(@PathVariable String voucherId) {
        try {
            voucherService.deleteVoucher(UUID.fromString(voucherId));
            return new ModelAndView("redirect:/voucher/list");
        } catch (RuntimeException e) {
            return new ModelAndView("/voucher/error","message", e.getMessage());
        }
    }

}
