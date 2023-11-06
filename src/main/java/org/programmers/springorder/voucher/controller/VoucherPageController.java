package org.programmers.springorder.voucher.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.programmers.springorder.voucher.dto.VoucherRequestDto;
import org.programmers.springorder.voucher.dto.VoucherResponseDto;
import org.programmers.springorder.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Profile("prod")
@Controller
public class VoucherPageController {
    private static final Logger log = LoggerFactory.getLogger(VoucherPageController.class);

    private final VoucherService voucherService;

    public VoucherPageController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String getVoucherList(Model model) {
        List<VoucherResponseDto> allVoucher = voucherService.getAllVoucher();
        model.addAttribute("voucherList", allVoucher);
        return "vouchers";
    }

    @GetMapping("/new-voucher")
    public String getNewVoucherPage(){
        return "new-voucher";
    }

    @PostMapping("/vouchers")
    public String createVoucher(VoucherRequestDto voucherRequestDto) {
        voucherService.saveNewVoucher(voucherRequestDto);
        return "redirect:/vouchers";
    }

    @GetMapping("/vouchers/{voucherId}")
    public String getVoucherDetail(@PathVariable UUID voucherId, Model model, HttpServletRequest request){
        try {
            VoucherResponseDto voucher = voucherService.getVoucherById(voucherId);
            model.addAttribute("voucher", voucher);
            return "voucher-detail";
        }catch (RuntimeException e){
            return "no-voucher";
        }
    }

    @DeleteMapping("/vouchers")
    public String deleteVoucher(@RequestParam(name = "voucherId", required = false) UUID voucherId){
        try {
            voucherService.deleteVoucher(voucherId);
            return "redirect:/vouchers";
        } catch (RuntimeException e){
            return "no-voucher";
        }
    }

    @GetMapping("/voucherAllocate/{voucherId}")
    public String getVoucherAllocatePage(@PathVariable UUID voucherId, Model model){
        try{
            model.addAttribute("voucherId", voucherId);
            return "voucher-allocate";
        }catch (RuntimeException e){
            return "no-voucher";
        }
    }
    @PostMapping("/voucherAllocate/{voucherId}")
    public String giveVoucher(@PathVariable UUID voucherId, @RequestParam UUID customerId){
        try {
            voucherService.allocateVoucher(voucherId, customerId);
            return "redirect:/vouchers";
        } catch (RuntimeException e){
            return "no-voucher";
        }
    }
}

