package org.prgrms.controller;

import java.util.List;
import java.util.UUID;
import org.prgrms.service.VoucherService;
import org.prgrms.voucher.voucherType.Voucher;
import org.prgrms.voucherDTO.VoucherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Profile("jdbc")
@RequestMapping("/voucher")
public class VoucherController {

    private final VoucherService voucherService;

    @Autowired
    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public String vouchers(Model model) {
        List<Voucher> vouchers = voucherService.findAll();
        model.addAttribute("vouchers", vouchers);
        return "voucher/voucherList";
    }

    @GetMapping("/{voucherId}")
    public String findVoucherById(@PathVariable UUID voucherId, Model model) {
        Voucher voucher = voucherService.findById(voucherId).get();
        model.addAttribute("voucher", voucher);
        return "voucher/voucherDetail";
    }

    @GetMapping("/createVoucher")
    public String goToCreateForm() {
        return "voucher/createVoucher";
    }

    @PostMapping("/createVoucher")
    public String createVoucher(VoucherDTO voucherDTO, Model model) {
        Voucher voucher = voucherDTO.getType().generateVoucher(voucherDTO.getAmount());
        voucherService.save(voucher);

        model.addAttribute("voucher", voucher);
        return  "redirect:/voucher/" + voucher.getVoucherId();
    }

    @DeleteMapping("/{voucherId}")
    public String deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteById(voucherId);
        return "redirect:";
    }



}
