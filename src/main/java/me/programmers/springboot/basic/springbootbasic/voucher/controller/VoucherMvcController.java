package me.programmers.springboot.basic.springbootbasic.voucher.controller;

import me.programmers.springboot.basic.springbootbasic.voucher.dto.VoucherCreateRequestDto;
import me.programmers.springboot.basic.springbootbasic.voucher.dto.VoucherUpdateRequestDto;
import me.programmers.springboot.basic.springbootbasic.voucher.model.FixedAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.PercentAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import me.programmers.springboot.basic.springbootbasic.voucher.service.JdbcVoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherMvcController {

    private final JdbcVoucherService voucherService;

    public VoucherMvcController(JdbcVoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String showVouchers(Model model) {
        List<Voucher> fixVouchers = voucherService.getAllFixVouchers();
        List<Voucher> percentVouchers = voucherService.getAllPercentVouchers();
        model.addAttribute("fixVouchers", fixVouchers);
        model.addAttribute("percentVouchers", percentVouchers);
        return "vouchers";
    }

    @GetMapping("/vouchers/{uuid}")
    public String showDetailVoucher(@PathVariable UUID uuid, Model model) {
        Voucher voucher = voucherService.getVoucherById(uuid);
        model.addAttribute("voucher", voucher);
        return "detail";
    }

    @GetMapping("/vouchers/new")
    public String createVoucher() {
        return "input";
    }

    @PostMapping("/vouchers/new")
    public String createVoucher(@ModelAttribute VoucherCreateRequestDto requestDto) {
        Voucher voucher = null;
        if (requestDto.getType().equals("fixed")) {
            voucher = new FixedAmountVoucher(UUID.randomUUID(), Long.parseLong(requestDto.getDiscountPrice()));
        } else if (requestDto.getType().equals("percent")) {
            voucher = new PercentAmountVoucher(UUID.randomUUID(), Long.parseLong(requestDto.getDiscountPercent()));
        }

        voucherService.save(voucher);

        return "redirect:/vouchers";
    }

    @PostMapping("/vouchers/update/{uuid}")
    public String updateVoucher(@PathVariable UUID uuid, @ModelAttribute VoucherUpdateRequestDto requestDto) {
        Voucher voucher = voucherService.getVoucherById(uuid);
        voucher.update(requestDto);
        voucherService.update(voucher);
        return "redirect:/vouchers";
    }

    @DeleteMapping("/vouchers/{uuid}")
    public String deleteVoucher(@PathVariable UUID uuid) {
        voucherService.deleteById(uuid);
        return "redirect:/vouchers";
    }

}
