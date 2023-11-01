package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.voucher.VoucherType;
import com.prgrms.vouchermanager.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

import static com.prgrms.vouchermanager.dto.voucher.VoucherRequest.*;
import static com.prgrms.vouchermanager.dto.voucher.VoucherResponse.*;


@Controller
@RequestMapping("/basic/vouchers")
@RequiredArgsConstructor
@Slf4j
public class VoucherWebController {
    private final VoucherService service;

    @GetMapping
    public String vouchers(Model model) {
        List<VoucherDetailResponse> response = service.findAll();
        model.addAttribute("vouchers", response);
        return "basic/vouchers/vouchers";
    }

    @GetMapping("/{voucherId}")
    public String voucher(@PathVariable UUID voucherId, Model model) {
        VoucherDetailResponse response = service.findById(voucherId);
        model.addAttribute("voucher", response);
        return "basic/vouchers/voucher";
    }

    @GetMapping("/create")
    public String createForm() {
        return "basic/vouchers/createForm";
    }

    @PostMapping("/create")
    public String create(@RequestBody VoucherCreateRequest voucherCreateRequest,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        VoucherDetailResponse response = service.create(voucherCreateRequest);
        redirectAttributes.addAttribute("voucher", response.voucherId());
        return "redirect:/basic/vouchers/{voucherId}";
    }

    @GetMapping("/{voucherId}/delete")
    public String delete(@PathVariable UUID voucherId) {
        service.delete(voucherId);
        return "basic/vouchers/delete";
    }

    @GetMapping("/{voucherId}/findById")
    public String findById(@PathVariable UUID voucherId, Model model) {
        VoucherDetailResponse voucher = service.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "basic/voucher";
    }

    @GetMapping("/findByCondition")
    public String findByCondition(@RequestParam String voucherType,
                                  @RequestParam int startYear,
                                  @RequestParam int startMonth,
                                  @RequestParam int endYear,
                                  @RequestParam int endMonth,
                                  Model model) {
        VoucherFindByConditionRequest request = toConditionVoucher(voucherType, startYear, startMonth, endYear, endMonth);
        List<VoucherDetailResponse> vouchers = service.findByCondition(request);
        log.info(vouchers.toString());
        model.addAttribute("vouchers", vouchers);
        return "basic/vouchers";
    }

    @ModelAttribute("voucherTypes")
    public VoucherType[] voucherTypes() {
        return VoucherType.values();
    }
}
