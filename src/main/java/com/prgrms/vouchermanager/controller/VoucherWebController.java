package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.voucher.VoucherType;
import com.prgrms.vouchermanager.service.VoucherService;
import com.prgrms.vouchermanager.util.VoucherFactory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/basic/vouchers")
@RequiredArgsConstructor
@Slf4j
public class VoucherWebController {
    private final VoucherService service;

    public Voucher create(VoucherType voucherType, long discount) {
        return service.create(voucherType, discount);
    }

    @GetMapping
    public String vouchers(Model model) {
        List<Voucher> vouchers = service.findAll();
        model.addAttribute("vouchers", vouchers);
        return "basic/vouchers";
    }

    @GetMapping("/{voucherId}")
    public String voucher(@PathVariable UUID voucherId, Model model) {
        Voucher voucher = service.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "basic/voucher";
    }

    @GetMapping("/create")
    public String createForm() {
        return "basic/createForm";
    }

    @PostMapping("/create")
    public String create(@RequestParam VoucherType voucherType,
                         @RequestParam int discount,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        Voucher voucher = service.create(voucherType, discount);
        redirectAttributes.addAttribute("voucherId", voucher.getId());
        model.addAttribute("voucher", voucher);
        return "redirect:/basic/vouchers/{voucherId}";
    }

    @GetMapping("/{voucherId}/delete")
    public String delete(@PathVariable UUID voucherId) {
        service.delete(voucherId);
        return "basic/delete";
    }

    @ModelAttribute("voucherType")
    public VoucherType[] voucherTypes() {
        return VoucherType.values();
    }

//    @PostConstruct
//    public void init() {
//        List<Voucher> list = service.findAll();
//        list.forEach(System.out::println);
//        service.create(VoucherType.FIXED, 20000);
//        service.create(VoucherType.PERCENT, 20);
//    }
}
