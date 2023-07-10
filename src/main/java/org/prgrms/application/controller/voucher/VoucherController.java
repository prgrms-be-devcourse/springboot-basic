package org.prgrms.application.controller.voucher;

import org.prgrms.application.controller.voucher.request.VoucherCreationRequest;
import org.prgrms.application.controller.voucher.request.VoucherDeleteRequest;
import org.prgrms.application.domain.voucher.Voucher;
import org.prgrms.application.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/voucher/")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

//    public List<Voucher> getStorage() {
//        return voucherService.getVouchers();
//    }

    @GetMapping(value = "creation")
    public String getVoucherCreationForm() {
        return "voucherCreation";
    }

    @PostMapping(value = "creation")
    public String createVoucher(@ModelAttribute VoucherCreationRequest request) {
        voucherService.createVoucher(request.getVoucherType(), request.getDiscountAmount());
        return "redirect:/";
    }

    @GetMapping(value = "/vouchers")
    public String findVouchers(Model model) {
        List<Voucher> vouchers = voucherService.getVouchers();
        model.addAttribute("vouchers", vouchers);
        return "vouchers";
    }




}