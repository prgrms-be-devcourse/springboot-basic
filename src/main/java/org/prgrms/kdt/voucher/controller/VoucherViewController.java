package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.voucher.controller.dto.CreateVoucherApiRequest;
import org.prgrms.kdt.voucher.controller.dto.SearchApiRequest;
import org.prgrms.kdt.voucher.controller.mapper.ControllerVoucherMapper;
import org.prgrms.kdt.voucher.service.dto.VoucherDetailResponse;
import org.prgrms.kdt.voucher.service.dto.VoucherResponses;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/view/vouchers")
public class VoucherViewController {
    private final VoucherService voucherService;
    private final ControllerVoucherMapper mapper;

    public VoucherViewController(VoucherService voucherService, ControllerVoucherMapper mapper) {
        this.voucherService = voucherService;
        this.mapper = mapper;
    }

    @GetMapping("/new")
    public String create() {
        return "voucher/voucher_create";
    }

    @PostMapping("/new")
    public String create(CreateVoucherApiRequest request) {
        voucherService.createVoucher(mapper.convertRequest(request));
        return "redirect:/view/vouchers";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable UUID id, Model model) {
        VoucherDetailResponse response = voucherService.findById(id);
        model.addAttribute("voucher", response);
        return "voucher/voucher_detail";
    }

    @GetMapping
    public String findAll(Model model) {
        SearchApiRequest request = new SearchApiRequest(1, 100, null);
        VoucherResponses response = voucherService.findAll(mapper.convertRequest(request));
        model.addAttribute("vouchers", response);
        return "voucher/vouchers";
    }

    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable UUID id) {
        voucherService.deleteById(id);
        return "redirect:/view/vouchers";
    }
}
