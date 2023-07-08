package org.prgrms.kdt.controller;

import lombok.RequiredArgsConstructor;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.request.voucher.CreateVoucherRequest;
import org.prgrms.kdt.service.voucher.VoucherService;
import org.prgrms.kdt.utils.VoucherType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    @GetMapping("/vouchers/new")
    public String makeVoucher(Model model) {
        return "vouchers/make-Voucher";
    }
    @ModelAttribute("voucherTypes")
    public VoucherType[] voucherTypes() {
        return VoucherType.values();
    }


    @PostMapping("/vouchers/new")
    public String addNewCustomer(CreateVoucherRequest createVoucherRequest) {
        voucherService.save(VoucherType.of(createVoucherRequest.voucherType()), createVoucherRequest.amount());
        return "redirect:/";
    }

    @GetMapping("/vouchers/list")
    public String showVoucherList(Model model) {
        List<Voucher> voucherList = voucherService.getVouchers();
        model.addAttribute("voucherList", voucherList);
        return "vouchers/voucher-list";
    }

    @GetMapping("/vouchers/view/{voucherId}")
    public String viewVoucher(Model model, @PathVariable("voucherId") Long voucherId) {
        model.addAttribute("voucher", voucherService.getVoucher(voucherId));
        return "vouchers/voucher-view";
    }



    @GetMapping("/vouchers/delete/{voucherId}")
    public String deleteVoucher( @PathVariable("voucherId") Long voucherId) {
        voucherService.deleteById(voucherId);
        return"redirect:/vouchers/list";
    }
}
