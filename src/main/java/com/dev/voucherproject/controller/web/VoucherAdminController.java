package com.dev.voucherproject.controller.web;


import com.dev.voucherproject.controller.web.request.VoucherCreateRequest;
import com.dev.voucherproject.model.service.VoucherService;
import com.dev.voucherproject.model.voucher.VoucherDto;
import com.dev.voucherproject.model.voucher.VoucherPolicy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/vouchers")
public class VoucherAdminController {
    private final VoucherService voucherService;

    public VoucherAdminController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/createForm")
    public String createForm(Model model) {
        model.addAttribute("policyTypes", VoucherPolicy.values());

        return "vouchers/createForm";
    }

    @PostMapping
    public String create(@ModelAttribute("voucher") VoucherCreateRequest voucherCreateRequest) {
        voucherService.insert(voucherCreateRequest);

        return "redirect:/admin/vouchers";
    }

    @GetMapping
    public String vouchers(Model model) {
        model.addAttribute("vouchers", voucherService.findAllVouchers());

        return "vouchers/vouchers";
    }

    @GetMapping("/{id}")
    public String voucher(@PathVariable String id, Model model) {
        VoucherDto voucher = voucherService.findById(id);
        model.addAttribute("voucher", voucher);

        return "vouchers/voucher";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        voucherService.deleteById(id);

        return "redirect:/admin/vouchers";
    }
}
