package org.programers.vouchermanagement.voucher.presentation;

import lombok.RequiredArgsConstructor;
import org.programers.vouchermanagement.voucher.application.VoucherService;
import org.programers.vouchermanagement.voucher.dto.request.VoucherCreationRequest;
import org.programers.vouchermanagement.voucher.dto.response.VoucherResponse;
import org.programers.vouchermanagement.voucher.dto.response.VouchersResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@RequestMapping("/view/vouchers")
@RequiredArgsConstructor
@Controller
public class VoucherViewController {

    private final VoucherService voucherService;

    @GetMapping
    public String save(Model model) {
        model.addAttribute("voucher", new VoucherResponse());
        return "vouchers/saveVoucher";
    }

    @PostMapping
    public String save(@Validated @ModelAttribute("voucher") VoucherCreationRequest request,
                       BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "vouchers/saveVoucher";
        }

        VoucherResponse response = voucherService.save(request);
        redirectAttributes.addAttribute("id", response.getId());
        return "redirect:/view/vouchers/{id}";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable UUID id, Model model) {
        VoucherResponse response = voucherService.findById(id);
        model.addAttribute("voucher", response);
        return "vouchers/voucher";
    }

    @GetMapping("/all")
    public String findAll(Model model) {
        VouchersResponse response = voucherService.findAll();
        model.addAttribute("vouchers", response);
        return "vouchers/vouchers";
    }

    @PostMapping("/{id}")
    public String deleteById(@PathVariable UUID id) {
        voucherService.deleteById(id);
        return "redirect:/view/vouchers/all";
    }
}
