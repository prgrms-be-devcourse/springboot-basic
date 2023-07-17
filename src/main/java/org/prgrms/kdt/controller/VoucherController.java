package org.prgrms.kdt.controller;

import lombok.RequiredArgsConstructor;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.request.voucher.CreateVoucherRequest;
import org.prgrms.kdt.service.voucher.VoucherService;
import org.prgrms.kdt.utils.VoucherType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vouchers")
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    @GetMapping("/new")
    public String makeVoucher() {
        return "vouchers/make-Voucher";
    }
    @ModelAttribute("voucherTypes")
    public VoucherType[] voucherTypes() {
        return VoucherType.values();
    }

    @PostMapping("/new")
    public String addNewCustomer(CreateVoucherRequest createVoucherRequest) {
        voucherService.save(VoucherType.of(createVoucherRequest.voucherType()), createVoucherRequest.amount());
        return "redirect:/";
    }

    @GetMapping("/list")
    public String showVoucherList(Model model) {
        List<Voucher> voucherList = voucherService.getVouchers();
        model.addAttribute("voucherList", voucherList);
        return "vouchers/voucher-list";
    }



    @GetMapping("/view/{voucherId}")
    public String viewVoucher(Model model, @PathVariable("voucherId") Long voucherId) {
        Model voucher = model.addAttribute("voucher", voucherService.getVoucherById(voucherId));
        return "vouchers/voucher-view";
    }


    @GetMapping("/delete/{voucherId}")
    public String deleteVoucher( @PathVariable("voucherId") Long voucherId) {
        voucherService.deleteById(voucherId);
        return "/";
    }
}
