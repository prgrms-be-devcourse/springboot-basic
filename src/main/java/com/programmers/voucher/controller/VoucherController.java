package com.programmers.voucher.controller;

import com.programmers.voucher.dto.VoucherDto;
import com.programmers.voucher.dto.VoucherRegisterForm;
import com.programmers.voucher.service.VoucherService;
import com.programmers.voucher.voucher.Voucher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

import static com.programmers.message.ErrorMessage.ERROR_INPUT_MESSAGE;

@Controller
@RequestMapping("/vouchers")
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    @GetMapping
    public String showVoucherList(Model model) {
        List<VoucherDto> voucherDtoList = voucherService.findAll();

        model.addAttribute("vouchers", voucherDtoList);
        return "/voucher/vouchers";
    }

    @GetMapping("/new")
    public String showCreatePage() {
        return "/voucher/newVouchers";
    }

    @PostMapping("/new")
    public String createVoucher(VoucherRegisterForm registerForm) {
        String type = registerForm.getType();
        String value = registerForm.getValue();

        validateVoucherForm(type, value);
        voucherService.register(type, value);

        return "redirect:/vouchers";
    }

    @GetMapping("/{voucherId}")
    public String voucherDetailPage(@PathVariable UUID voucherId, Model model) {
        VoucherDto voucherDto = voucherService.getVoucher(voucherId);
        model.addAttribute("voucher", voucherDto);
        return "/voucher/voucherDetail";
    }

    @PostMapping("/delete/{voucherId}")
    public String deleteVoucher(@PathVariable String voucherId) {
        UUID uuid = UUID.fromString(voucherId);
        voucherService.deleteVoucher(uuid);

        return "redirect:/vouchers";
    }

    private void validateVoucherForm(String type, String value) {
        if (!StringUtils.hasText(type) || !StringUtils.hasText(value)) {
            throw new RuntimeException(ERROR_INPUT_MESSAGE.getMessage());
        }
    }
}
