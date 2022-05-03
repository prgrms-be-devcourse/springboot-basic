package org.prgrms.kdtspringdemo.domain.voucher.controller;

import org.prgrms.kdtspringdemo.domain.customer.data.Customer;
import org.prgrms.kdtspringdemo.domain.customer.data.CustomerDto;
import org.prgrms.kdtspringdemo.domain.voucher.VoucherService;
import org.prgrms.kdtspringdemo.domain.voucher.data.Voucher;
import org.prgrms.kdtspringdemo.domain.voucher.data.VoucherDto;
import org.prgrms.kdtspringdemo.domain.voucher.type.VoucherType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/voucher")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/list")
    public String voucherList(Model model){
        List<Voucher> vouchers = voucherService.findAll();
        model.addAttribute("vouchers", vouchers);
        return "/voucher/voucher-list";
    }

    @GetMapping("/{voucherId}")
    public String voucherDetail(@PathVariable UUID voucherId, Model model){
        Voucher voucher = voucherService.findById(voucherId);
        model.addAttribute("voucher", voucher);

        return "/voucher/voucher-detail";
    }

    @GetMapping("/delete")
    public String allVoucherDelete(){
        voucherService.deleteAll();

        return "/voucher/voucher-list";
    }

    @GetMapping("/newvoucher")
    public String newVoucher(Model model){
        model.addAttribute("voucherDto", new VoucherDto(null,null));
        return "/voucher/new-voucher";
    }


    @PostMapping("/newvoucher")
    public String newVoucherPost(@ModelAttribute VoucherDto voucherDto){
        if(voucherDto.type().equals("FIXED")){
            voucherService.saveVoucherInDB(Math.toIntExact(voucherDto.amount()),VoucherType.FIXED);
        }else{
            voucherService.saveVoucherInDB(Math.toIntExact(voucherDto.amount()),VoucherType.PERCENT);
        }
        return "redirect:/";
    }

    @GetMapping("/findwithId")
    public String findByIdVoucher(Model model){
        List<Voucher> vouchers = voucherService.findAll();
        model.addAttribute("vouchers", vouchers);
        return "/voucher/findwithId";
    }

    @PostMapping("/findwithId")
    public String findByIdVoucherPost(@PathVariable String voucherId, Model model){
        Voucher voucher = voucherService.findById(UUID.fromString(voucherId));
        model.addAttribute("voucher", voucher);
        return "redirect:/voucher/findwithIdDetail";
    }


    @GetMapping("/percent")
    public String percentVoucher(Model model){
        List<Voucher> vouchers = voucherService.findByPercent();
        model.addAttribute("vouchers", vouchers);
        return "/voucher/findwithIdDetail";
    }


    @GetMapping("/fixed")
    public String fixedVoucher(Model model){
        List<Voucher> vouchers = voucherService.findByFixed();
        model.addAttribute("vouchers", vouchers);
        return "/voucher/findwithIdDetail";
    }
}
