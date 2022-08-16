package com.prgrms.kdt.springbootbasic.voucher.controller;

import com.prgrms.kdt.springbootbasic.VoucherList;
import com.prgrms.kdt.springbootbasic.dto.request.CreateVoucherRequest;
import com.prgrms.kdt.springbootbasic.dto.request.UpdateVoucherRequest;
import com.prgrms.kdt.springbootbasic.voucher.entity.Voucher;
import com.prgrms.kdt.springbootbasic.voucher.service.VoucherService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@Profile("jdbc")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String voucherList(Model model){
        List<Voucher> vouchers = voucherService.getAllVouchers();
        model.addAttribute("vouchers", vouchers);

        return "voucher-list";
    }

    @GetMapping("/new-voucher")
    public String newVoucherPage(){
        return "new-voucher";
    }

    @PostMapping("/vouchers")
    public String createVoucher(CreateVoucherRequest voucherRequest){
        voucherService.saveVoucher(voucherRequest.voucherType(), voucherRequest.amount());
        return "redirect:/vouchers";
    }

    @GetMapping("/vouchers/{voucherId}")
    public String updateVoucherPage(@PathVariable("voucherId") UUID voucherId, Model model){
        Voucher voucher = voucherService.getVoucherById(voucherId);
        model.addAttribute("voucher", voucher);
        return "update-voucher";
    }

    @PostMapping("/vouchers/{voucherId}")
    public String updateVoucher(@PathVariable("voucherId") UUID voucherId, UpdateVoucherRequest voucherRequest){
        voucherService.updateVoucher(VoucherList.makeVoucherByType(voucherRequest.voucherType(), voucherId, voucherRequest.amount(), LocalDateTime.now()));
        return "redirect:/vouchers";
    }

    @GetMapping("/vouchers/delete/{voucherId}")
    public String deleteVoucher(@PathVariable("voucherId") UUID voucherId){
        voucherService.deleteVoucher(voucherId);
        return "redirect:/vouchers";
    }
}
