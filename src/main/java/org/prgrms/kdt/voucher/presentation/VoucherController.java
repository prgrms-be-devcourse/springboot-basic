package org.prgrms.kdt.voucher.presentation;

import org.prgrms.kdt.customer.domain.vo.Email;
import org.prgrms.kdt.voucher.application.VoucherService;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.vo.Type;
import org.prgrms.kdt.voucher.dto.VoucherDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/voucher/create")
    public String createVoucher(Model model) {
        VoucherDto dto = new VoucherDto();
        model.addAttribute("voucher", dto);
        return "/voucher/create-voucher";
    }

    @PostMapping("/voucher/create")
    public String createVoucher(VoucherDto dto) {
        voucherService.createVoucher(dto);
        return "redirect:/voucher/create";
    }

    @GetMapping("/voucher")
    public String vouchers(Model model) {
        List<Voucher> vouchers = voucherService.findAll();
        model.addAttribute("vouchers", vouchers);
        return "/voucher/manage-voucher";
    }


    @PostMapping("/vouchers")
    public String vouchers(Model model, Email email) {
        List<Voucher> vouchers = voucherService.findByEmail(email);
        model.addAttribute("vouchers", vouchers);
        return "/voucher/manage-voucher";
    }

    @GetMapping("/voucher/fixed")
    public String manageFixedVouchers(Model model) {
        List<Voucher> vouchers = voucherService.findByType(Type.FIXED);
        model.addAttribute("vouchers", vouchers);
        return "/voucher/manage-voucher";
    }

    @GetMapping("/voucher/percent")
    public String managePercentVouchers(Model model) {
        List<Voucher> vouchers = voucherService.findByType(Type.PERCENT);
        model.addAttribute("vouchers", vouchers);
        return "/voucher/manage-voucher";
    }

    @GetMapping("/voucher/{id}/delete")
    public String deleteVoucher(@PathVariable("id") UUID id) {
        voucherService.deleteById(id);
        return "/voucher/manage-voucher";
    }

}
