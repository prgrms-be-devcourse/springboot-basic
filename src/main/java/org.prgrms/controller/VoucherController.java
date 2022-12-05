package org.prgrms.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.prgrms.exception.NotFoundVoucherException;
import org.prgrms.service.VoucherService;
import org.prgrms.voucher.voucherType.Voucher;
import org.prgrms.dto.VoucherDTO;
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
        Voucher voucher = voucherService.findById(voucherId)
            .orElseThrow(() -> new NotFoundVoucherException(
                "해당 id를 가진 바우처를 조회할 수 없습니다 *id:" + voucherId.toString()));
        model.addAttribute("voucher", voucher);
        return "voucher/voucherDetail";
    }

    @GetMapping("/createVoucher")
    public String goToCreateForm() {
        return "voucher/createVoucher";
    }

    @PostMapping("/createVoucher")
    public String createVoucher(@Valid VoucherDTO voucherDTO, Model model) {
        Voucher voucher = voucherService.save(voucherDTO);

        model.addAttribute("voucher", voucher);
        return "redirect:/voucher/" + voucher.getVoucherId();
    }

    @DeleteMapping("/{voucherId}")
    public String deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteById(voucherId);
        return "redirect:";
    }

}
