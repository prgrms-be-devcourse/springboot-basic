package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.voucher.controller.dto.ControllerCreateVoucherRequest;
import org.prgrms.kdt.voucher.controller.mapper.ControllerVoucherMapper;
import org.prgrms.kdt.voucher.service.dto.VoucherDetailResponse;
import org.prgrms.kdt.voucher.service.dto.VoucherResponses;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/view/voucher")
public class VoucherViewController {
    private final VoucherService voucherService;
    private final ControllerVoucherMapper mapper;

    public VoucherViewController(VoucherService voucherService, ControllerVoucherMapper mapper) {
        this.voucherService = voucherService;
        this.mapper = mapper;
    }

    @GetMapping("/new")
    public String save() {
        return "voucher/voucher_create";
    }

    @PostMapping("/new")
    public String create(ControllerCreateVoucherRequest request) {
        voucherService.createVoucher(mapper.controllerDtoToServiceDto(request));
        return "redirect:/voucher";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable UUID id, Model model) {
        VoucherDetailResponse response = voucherService.findById(id);
        model.addAttribute("voucher", response);
        return "voucher/voucher_detail";
    }

    @GetMapping
    public String findAll(Model model) {
        VoucherResponses response = voucherService.findAll();
        model.addAttribute("vouchers", response);
        return "voucher/vouchers";
    }

    @PostMapping("/{id}")
    public String deleteById(@PathVariable UUID id) {
        voucherService.deleteById(id);
        return "redirect:/voucher";
    }
}
