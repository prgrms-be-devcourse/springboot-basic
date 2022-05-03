package org.programmers.kdt.weekly.voucher.controller;

import java.util.UUID;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.programmers.kdt.weekly.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping("/new-voucher")
    public String createVoucherPage(Model model) {
        model.addAttribute("voucherType", VoucherType.values());

        return "new-voucher";
    }

    @PostMapping("/new-voucher")
    public String create(CreateVoucherRequest createVoucherRequest) {
        voucherService.create(createVoucherRequest.getVoucherId(),
            createVoucherRequest.getVoucherType(), createVoucherRequest.getValue());

        return "redirect:/";
    }

    @GetMapping("/voucher")
    public String list(Model model) {
        var vouchers = voucherService.getVouchers();
        model.addAttribute("vouchers", vouchers);

        return "voucher-list";
    }

    @GetMapping("/voucher/{voucherId}")
    public String detail(@PathVariable("voucherId") UUID voucherId, Model model) {
        var voucher = voucherService.findById(voucherId);
        model.addAttribute("voucher", voucher.get());

        return "voucher-detail";
    }

    @DeleteMapping("/voucher/{voucherId}")
    public String delete(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteById(voucherId);

        return "redirect:/voucher";
    }

    @PostMapping("/voucher/{voucherId}")
    public String update(@PathVariable("voucherId") UUID voucherId,
        CreateVoucherRequest createVoucherRequest) {
        var voucher = voucherService.findById(voucherId).get();
        voucher.changeValue(createVoucherRequest.getValue());
        voucherService.update(voucher);

        return "redirect:/voucher";
    }
}
