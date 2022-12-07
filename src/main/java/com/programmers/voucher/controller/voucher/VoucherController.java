package com.programmers.voucher.controller.voucher;

import com.programmers.voucher.controller.voucher.dto.VoucherAssignRequest;
import com.programmers.voucher.controller.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.controller.voucher.dto.VoucherUpdateRequest;
import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
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
        return "redirect:/vouchers";
    }

    @GetMapping("/list")
    public String findAll(Model model) {
        List<Voucher> vouchers = voucherService.findAll();
        model.addAttribute("vouchers", vouchers);
        return "voucher/voucher_list";
    }

    @GetMapping("/{voucherId}")
    public String findById(@PathVariable UUID voucherId, Model model) {
        Voucher voucher = voucherService.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher/voucher_detail";
    }

    @GetMapping
    public String findAllByCustomer(@RequestParam String email, Model model) {
        List<Voucher> vouchers = voucherService.findAllByCustomer(email);
        model.addAttribute("vouchers", vouchers);
        return "voucher/voucher_list";
    }

    @PutMapping
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
