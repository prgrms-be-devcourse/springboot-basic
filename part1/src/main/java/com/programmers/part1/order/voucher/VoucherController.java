package com.programmers.part1.order.voucher;

import com.programmers.part1.domain.voucher.CreateRequestVoucher;
import com.programmers.part1.domain.voucher.Voucher;
import com.programmers.part1.domain.voucher.VoucherType;
import com.programmers.part1.exception.ResponseEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    @GetMapping
    public String viewAllVoucher(Model model){
        model.addAttribute("vouchers", voucherService.getAllVoucher());
        return "/voucher/vouchers";
    }

    @GetMapping("/new")
    public String viewAddForm(Model model){
        model.addAttribute("voucherTypes",VoucherType.values());
        return "/voucher/voucher-add";
    }

    @PostMapping("/new")
    public String addVoucher(CreateRequestVoucher createRequestVoucher){
        voucherService.createVoucher(VoucherType.valueOf(createRequestVoucher.getVoucherType()), createRequestVoucher.getAmount());
        return "redirect:/vouchers";
    }

    @GetMapping("/{voucherId}")
    public String viewVoucher(@PathVariable UUID voucherId, Model model){
        Voucher voucher = voucherService.getVoucherById(voucherId).orElseThrow(()->new ResponseEmptyException("바우처가 없습니다."));
        model.addAttribute("voucher",voucher);
        return "/voucher/voucher-detail";
    }

    @PostMapping("{voucherId}")
    public String deleteVoucher(@PathVariable UUID voucherId){
        voucherService.deleteVoucherById(voucherId);
        return "redirect:/vouchers";
    }

}
