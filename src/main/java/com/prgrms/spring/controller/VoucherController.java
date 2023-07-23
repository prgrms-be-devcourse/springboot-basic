package com.prgrms.spring.controller;

import com.prgrms.spring.controller.dto.request.VoucherCreateRequestDto;
import com.prgrms.spring.domain.voucher.Voucher;
import com.prgrms.spring.service.VoucherService;
import com.prgrms.spring.service.VoucherServiceForView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/voucher")
public class VoucherController {

    private final VoucherServiceForView voucherServiceForView;

    @GetMapping("")
    public String voucherPage(Model model) {
        List<Voucher> vouchers = voucherServiceForView.getAllVoucher();
        model.addAttribute("vouchers", vouchers);
        return "voucher-list";
    }

    @GetMapping("/new")
    public String newVoucherPage() {
        return "new-voucher";
    }

    @PostMapping("")
    public String newVoucher(VoucherCreateRequestDto requestDto) {
        System.out.println(requestDto.getVoucherType());
        System.out.println(requestDto.getDiscount());
        voucherServiceForView.createVoucher(requestDto);
        return "redirect:/voucher";
    }

    @GetMapping("/{voucherId}")
    public String voucherDetailPage(Model model, @PathVariable String voucherId) {
        Voucher voucher = voucherServiceForView.getVoucherById(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher-detail";
    }

    @PostMapping("/{voucherId}")
    public String deleteVoucher(@PathVariable String voucherId) {
        voucherServiceForView.deleteVoucher(voucherId);
        return "redirect:/voucher";
    }
}
