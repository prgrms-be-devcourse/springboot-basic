package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.voucher.Voucher;
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
        VoucherListResponse response = new VoucherListResponse(service.findAll());
        model.addAttribute("vouchers", response.vouchers());
        return "basic/vouchers";
    }

    @GetMapping("/{voucherId}")
    public String voucher(@PathVariable UUID voucherId, Model model) {
        VoucherDetailResponse response = toDetailVoucher(service.findById(voucherId));
        model.addAttribute("voucherId", response.voucherId());
        model.addAttribute("voucherType", response.voucherType());
        model.addAttribute("voucherDiscount", response.discount());
        return "basic/voucher";
    }

    @GetMapping("/create")
    public String createForm() {
        return "basic/createForm";
    }

    @PostMapping("/create")
    public String create(@RequestBody VoucherCreateRequest voucherCreateRequest,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        Voucher voucher = service.create(voucherCreateRequest);
        redirectAttributes.addAttribute("voucherId", voucher.getId());
        return "redirect:/basic/vouchers/{voucherId}";
    }

    @GetMapping("/{voucherId}/delete")
    public String delete(@PathVariable UUID voucherId) {
        service.delete(voucherId);
        return "basic/delete";
    }

    @GetMapping("/{voucherId}/findById")
    public String findById(@PathVariable UUID voucherId, Model model) {
        Voucher voucher = service.findById(voucherId);
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
        List<Voucher> vouchers
                = service.findByCondition(request);
        log.info(vouchers.toString());
        model.addAttribute("vouchers", vouchers);
        return "basic/vouchers";
    }

    @ModelAttribute("voucherTypes")
    public VoucherType[] voucherTypes() {
        return VoucherType.values();
    }
}
