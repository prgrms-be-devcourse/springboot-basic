package com.programmers.springmission.voucher.presentation;

import com.programmers.springmission.voucher.application.VoucherService;
import com.programmers.springmission.voucher.domain.enums.VoucherType;
import com.programmers.springmission.voucher.presentation.request.VoucherCreateRequest;
import com.programmers.springmission.voucher.presentation.request.VoucherUpdateRequest;
import com.programmers.springmission.voucher.presentation.response.VoucherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/voucher")
@RequiredArgsConstructor
public class VoucherViewController {

    private static final String REDIRECT_VOUCHER = "redirect:/voucher";

    private final VoucherService voucherService;

    @GetMapping
    public String viewVoucherPage(Model model) {
        List<VoucherResponse> allVoucher = voucherService.findAllVoucher();
        model.addAttribute("serverTime", LocalDateTime.now());
        model.addAttribute("vouchers", allVoucher);
        return "voucher/voucher";
    }

    @GetMapping("/{voucherId}")
    public String findVoucher(@PathVariable("voucherId") UUID voucherId, Model model) {
        VoucherResponse voucherResponse = voucherService.findOneVoucher(voucherId);

        if (voucherResponse != null) {
            model.addAttribute("voucher", voucherResponse);
            return "voucher/voucher-details";
        } else {
            return "global/error";
        }
    }

    @PostMapping("/{voucherId}")
    public String deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
        return REDIRECT_VOUCHER;
    }

    @GetMapping("/viewNewPage")
    public String viewNewPage(Model model) {
        model.addAttribute("voucherTypes", VoucherType.values());
        return "voucher/voucher-new";
    }

    @PostMapping("/viewNewPage")
    public String addNewVoucher(@ModelAttribute("voucher") VoucherCreateRequest voucherCreateRequest) {
        voucherService.createVoucher(voucherCreateRequest);
        return REDIRECT_VOUCHER;
    }

    @GetMapping("/viewModifyPage/{voucherId}")
    public String viewModifyPage(@PathVariable("voucherId") UUID voucherId, Model model) {
        VoucherResponse voucherResponse = voucherService.findOneVoucher(voucherId);

        if (voucherResponse != null) {
            model.addAttribute("voucher", voucherResponse);
            return "voucher/voucher-modify";
        } else {
            return "global/error";
        }
    }

    @PostMapping("/viewModifyPage/amount/{voucherId}")
    public String modifyAmount(@PathVariable("voucherId") UUID voucherId,
                               @ModelAttribute("voucher") VoucherUpdateRequest voucherUpdateRequest) {
        voucherService.updateAmount(voucherId, voucherUpdateRequest);
        return REDIRECT_VOUCHER;
    }

    @PostMapping("/viewModifyPage/newRegistration/{voucherId}")
    public String modifyCustomer(@PathVariable("voucherId") UUID voucherId, String customerId) {
        voucherService.updateCustomer(voucherId, UUID.fromString(customerId.trim()));
        return REDIRECT_VOUCHER;
    }
}
