package com.program.commandLine.controller.view;

import com.program.commandLine.model.VoucherInputData;
import com.program.commandLine.model.voucher.Voucher;
import com.program.commandLine.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Controller
public class VoucherViewController {

    private final VoucherService voucherService;

    private static final Logger logger = LoggerFactory.getLogger(VoucherViewController.class);


    public VoucherViewController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping( "/vouchers")
    public String viweVochersPage(Model model){
        var allVouches = voucherService.getAllVoucher();
        model.addAttribute("vouchers",allVouches);
        return "vouchers";
    }

    @GetMapping( "new-voucher")
    public String newVoucherPage(Model model){
        return "new-voucher";
    }

    @PostMapping( "new-voucher")
    public String saveVoucher(VoucherInputData voucherInputData){
        voucherService.createVoucher(voucherInputData);
        return "redirect:/vouchers";
    }

    @GetMapping( "/vouchers/{voucherId}")
    public String viewVoucherDetailPage(@PathVariable("voucherId")UUID voucherId, Model model){
        Optional<Voucher> maybeVoucher = voucherService.getVoucherById(voucherId);
        if(maybeVoucher.isPresent()){
            model.addAttribute("voucher",maybeVoucher.get());
            return "voucher-detail";
        }
       return "404";
    }

    @DeleteMapping( "vouchers/{voucherId}")
    public String deleteVoucher(@PathVariable("voucherId")UUID voucherId){
        voucherService.deleteVoucher(voucherId);
        return "redirect:/vouchers";
    }
}
