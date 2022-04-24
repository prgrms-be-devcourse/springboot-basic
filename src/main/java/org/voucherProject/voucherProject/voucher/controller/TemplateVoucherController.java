package org.voucherProject.voucherProject.voucher.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.voucherProject.voucherProject.voucher.entity.Voucher;
import org.voucherProject.voucherProject.voucher.controller.dto.VoucherDto;
import org.voucherProject.voucherProject.voucher.entity.VoucherType;
import org.voucherProject.voucherProject.voucher.service.VoucherService;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/template")
public class TemplateVoucherController {

    private final VoucherService voucherService;

    @ModelAttribute("voucherTypes")
    public VoucherType[] voucherTypes() {
        return VoucherType.values();
    }

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("voucherDto", new VoucherDto());
        return "createForm";
    }

    @PostMapping("/new")
    public String createVoucher(@ModelAttribute("voucherDto") VoucherDto voucherDto) {
        Voucher voucher = voucherDto.getVoucherType().createVoucher(
                voucherDto.getAmount(),
                voucherDto.getCustomerId());
        voucherService.save(voucher);
        return "redirect:";
    }

    @GetMapping("/list/{id}")
    public String findById(@PathVariable("id") UUID voucherId, Model model) {
        VoucherDto voucherDto = VoucherDto.of(voucherService.findById(voucherId));
        model.addAttribute("voucherDto", voucherDto);
        return "voucher";
    }

    @GetMapping("/list")
    public String findAll(Model model) {
        List<VoucherDto> voucherDtos = voucherService.findAll()
                .stream()
                .map(VoucherDto::of).toList();
        model.addAttribute("voucherDtos", voucherDtos);
        return "list";
    }

    @GetMapping("/order")
    public String orderForm(Model model) {
        model.addAttribute("voucherDto", new VoucherDto());
        return "orderForm";
    }

    @PostMapping("/order")
    public String useVoucher(@ModelAttribute("voucherDto") VoucherDto voucherDto) {
        Voucher voucher = voucherService.findById(voucherDto.getVoucherId());
        voucherService.useVoucher(voucher);
        return "redirect:";
    }

    @GetMapping("/cancel")
    public String cancelForm(Model model) {
        model.addAttribute("voucherDto", new VoucherDto());
        return "cancelForm";
    }

    @PostMapping("/cancel")
    public String cancelVoucher(@ModelAttribute("voucherDto") VoucherDto voucherDto) {
        Voucher voucher = voucherService.findById(voucherDto.getVoucherId());
        voucherService.cancelVoucher(voucher);
        return "redirect:";
    }

    @GetMapping("/delete")
    public String deleteForm(Model model) {
        model.addAttribute("voucherDto", new VoucherDto());
        return "deleteForm";
    }

    @PostMapping("/delete")
    public String deleteVoucher(@ModelAttribute VoucherDto voucherDto) {
        UUID voucherId = voucherDto.getVoucherId();
        UUID customerId = voucherDto.getCustomerId();
        voucherService.deleteOneVoucherByCustomer(customerId, voucherId);
        return "redirect:";
    }

    @GetMapping("/list-type")
    public String findByVoucherType(@RequestParam("type") String voucherType, Model model) {
        List<Voucher> listByVoucherType = voucherService.findByVoucherType(VoucherType.valueOf(voucherType.toUpperCase()));
        List<VoucherDto> voucherDtos = listByVoucherType.stream().map(VoucherDto::of).toList();
        model.addAttribute("voucherDtos", voucherDtos);
        return "list";
    }
}
