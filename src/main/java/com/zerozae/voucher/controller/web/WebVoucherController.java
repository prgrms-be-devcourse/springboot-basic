package com.zerozae.voucher.controller.web;

import com.zerozae.voucher.dto.voucher.VoucherCreateRequest;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;
import com.zerozae.voucher.service.voucher.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

import static com.zerozae.voucher.validator.InputValidator.validateInputUuid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vouchers")
public class WebVoucherController {

    private final VoucherService voucherService;

    @GetMapping("/createForm")
    public String getCreateForm(){
        return "/voucher/createForm";
    }

    @PostMapping
    public String createVoucher(@ModelAttribute VoucherCreateRequest voucherRequest){
        voucherService.createVoucher(voucherRequest);
        return "redirect:/vouchers";
    }

    @GetMapping
    public String findAllVouchers(Model model){
        List<VoucherResponse> vouchers = voucherService.findAllVouchers();
        model.addAttribute("vouchers", vouchers);
        return "/voucher/vouchers";
    }

    @GetMapping("/{voucherId}")
    public String voucherDetailPage(@PathVariable("voucherId") String voucherId, Model model){
        validateInputUuid(voucherId);
        VoucherResponse voucher = voucherService.findById(UUID.fromString(voucherId));
        model.addAttribute("voucher", voucher);
        return "/voucher/voucherDetail";
    }

    @GetMapping("/update/{voucherId}")
    public String getUpdateForm(@PathVariable("voucherId") String voucherId, Model model){
        validateInputUuid(voucherId);
        VoucherResponse voucher = voucherService.findById(UUID.fromString(voucherId));
        model.addAttribute("voucher", voucher);
        return "voucher/updateForm";
    }

    @PatchMapping("/{voucherId}")
    public String updateVoucher(@PathVariable("voucherId") String voucherId,
                                @ModelAttribute VoucherUpdateRequest voucherUpdateRequest){

        validateInputUuid(voucherId);
        voucherService.update(UUID.fromString(voucherId), voucherUpdateRequest);
        return "redirect:/vouchers";
    }

    @DeleteMapping("/{voucherId}")
    public String deleteVoucher(@PathVariable("voucherId") String voucherId){
        validateInputUuid(voucherId);
        voucherService.deleteById(UUID.fromString(voucherId));
        return "redirect:/vouchers";
    }
}
