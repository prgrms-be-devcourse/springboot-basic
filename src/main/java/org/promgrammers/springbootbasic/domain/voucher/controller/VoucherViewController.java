package org.promgrammers.springbootbasic.domain.voucher.controller;

import lombok.RequiredArgsConstructor;
import org.promgrammers.springbootbasic.domain.voucher.dto.request.CreateVoucherRequest;
import org.promgrammers.springbootbasic.domain.voucher.dto.response.VoucherListResponse;
import org.promgrammers.springbootbasic.domain.voucher.dto.response.VoucherResponse;
import org.promgrammers.springbootbasic.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
@RequiredArgsConstructor
public class VoucherViewController {

    private final VoucherService voucherService;

    @GetMapping
    public String save() {
        return "vouchers/saveForm";
    }

    @PostMapping
    public String save(@ModelAttribute("voucher") CreateVoucherRequest request,
                       RedirectAttributes redirectAttributes) {

        VoucherResponse voucher = voucherService.create(request);
        redirectAttributes.addAttribute("id", voucher.voucherId());

        return "redirect:/vouchers/{id}";
    }

    @GetMapping("list")
    public String findAll(Model model) {
        VoucherListResponse voucherListResponse = voucherService.findAll();
        model.addAttribute("vouchers", voucherListResponse);

        return "vouchers/vouchers";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable UUID id, Model model) {
        VoucherResponse voucherResponse = voucherService.findById(id);
        model.addAttribute("voucher", voucherResponse);

        return "vouchers/voucher";
    }

    @PostMapping("/{id}")
    public String deleteById(@PathVariable UUID id) {
        voucherService.deleteById(id);

        return "redirect:/vouchers/list";
    }
}
