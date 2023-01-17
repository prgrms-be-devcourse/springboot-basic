package com.programmers.voucher.controller.voucher;

import com.programmers.voucher.controller.voucher.dto.VoucherAssignRequest;
import com.programmers.voucher.controller.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.controller.voucher.dto.VoucherUpdateRequest;
import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @GetMapping("/add")
    public String create() {
        return "voucher/voucher_new";
    }

    @PostMapping("/add")
    public String create(VoucherCreateRequest voucherCreateRequest) {
        voucherService.create(voucherCreateRequest);
        return "redirect:/vouchers/list";
    }

    @GetMapping("/list")
    public String findAll(@RequestParam(required = false) String email, Model model) {
        List<Voucher> vouchers = voucherService.findAll(email);
        model.addAttribute("vouchers", vouchers);
        return "voucher/voucher_list";
    }

    @GetMapping("/{voucherId}")
    public String findById(@PathVariable UUID voucherId, Model model) {
        Voucher voucher = voucherService.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher/voucher_detail";
    }

    @PutMapping("/update")
    public String update(VoucherUpdateRequest voucherUpdateRequest) {
        voucherService.update(voucherUpdateRequest);
        return "redirect:/vouchers/list";
    }

    @GetMapping("/assign/{voucherId}")
    public String assign(@PathVariable UUID voucherId, Model model) {
        Voucher voucher = voucherService.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher/voucher_assign";
    }

    @PostMapping("/assign")
    public String assign(VoucherAssignRequest voucherAssignRequest) {
        voucherService.assign(voucherAssignRequest.voucherId(), voucherAssignRequest.email());
        return "redirect:/vouchers/list";
    }
}
