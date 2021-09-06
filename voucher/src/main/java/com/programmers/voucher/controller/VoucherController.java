package com.programmers.voucher.controller;

import com.programmers.voucher.service.voucher.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/voucher")
public class VoucherController {

    private final VoucherService voucherService;

    static final List<String[]> links = new ArrayList<>();
    static final String LINKS_MODEL_ATTRIBUTE = "links";

    static {
        links.add(new String[]{"Main", "/voucher"});
        links.add(new String[]{"Create Voucher", "/voucher/create"});
        links.add(new String[]{"Read Voucher", "/voucher/read"});
        links.add(new String[]{"List Vouchers", "/voucher/list"});
        links.add(new String[]{"Update Vouchers", "/voucher/update"});
        links.add(new String[]{"Delete Voucher", "/voucher/delete"});
    }

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/read")
    public String readVoucher(@RequestParam(name = "id", defaultValue = "") Long id,
                              Model model) {
        model.addAttribute(LINKS_MODEL_ATTRIBUTE, links);
        if (id == null) return "voucher/read";
        model.addAttribute("id", id);
        voucherService.findById(id).ifPresentOrElse(
                voucher -> model.addAttribute("voucher", voucher),
                () -> model.addAttribute("error", "No voucher found."));
        return "voucher/read";
    }

}
