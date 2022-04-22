package org.voucherProject.voucherProject.voucher.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.voucherProject.voucherProject.voucher.entity.Voucher;
import org.voucherProject.voucherProject.voucher.entity.VoucherDto;
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

    @GetMapping("/{id}")
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

    /**
     * GetMapping
     */
    @PutMapping("/order")
    public String useVoucher(@RequestBody VoucherDto voucherDto, Model model) {
        Voucher voucher = voucherService.findById(voucherDto.getVoucherId());
        voucher.useVoucher();
        Voucher useVoucher = voucherService.updateVoucher(voucher);
        model.addAttribute("updateVoucher", useVoucher);
        return "redirect:";
    }


    /**
     * GetMapping
     */
    @PutMapping("/cancel")
    public String cancelVoucher(@RequestBody VoucherDto voucherDto, Model model) {
        Voucher voucher = voucherService.findById(voucherDto.getVoucherId());
        voucher.cancelVoucher();
        Voucher cancelVoucher = voucherService.updateVoucher(voucher);
        model.addAttribute(cancelVoucher);
        return "redirect:";
    }


    /**
     * GetMapping
     */
    @DeleteMapping("/delete")
    public String deleteVoucher(@RequestBody VoucherDto voucherDto) {
        UUID voucherId = voucherDto.getVoucherId();
        UUID customerId = voucherDto.getCustomerId();
        voucherService.deleteOneVoucherByCustomer(customerId, voucherId);
        return "redirect:";
    }

    @GetMapping("/list-type")
    public String findByVoucherType(@RequestParam("type") String voucherType, Model model) {
        List<Voucher> byVoucherType = voucherService.findByVoucherType(VoucherType.valueOf(voucherType.toUpperCase()));
        List<VoucherDto> voucherDtos = byVoucherType.stream().map(VoucherDto::of).toList();
        model.addAttribute("voucherDtos", voucherDtos);
        return "list";
    }

    @GetMapping("/list-created-at")
    public String findByCreatedAtBetweenAandB(@RequestParam("date1") String date1,
                                              @RequestParam("date2") String date2,
                                              Model model) {
        List<Voucher> byCreatedAtBetween = voucherService.findByCreatedAtBetween(date1, date2);
        List<VoucherDto> voucherDtos = byCreatedAtBetween.stream().map(VoucherDto::of).toList();
        model.addAttribute("voucherDtos", voucherDtos);
        return "list";
    }
}
