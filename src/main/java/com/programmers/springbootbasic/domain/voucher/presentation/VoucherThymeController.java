package com.programmers.springbootbasic.domain.voucher.presentation;

import com.programmers.springbootbasic.domain.voucher.application.VoucherService;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.CreateVoucherRequest;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.UpdateVoucherRequest;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.VoucherResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Profile(value = "tomcat")
@Controller
@RequestMapping("/voucher")
public class VoucherThymeController {

    private final VoucherService voucherService;

    public VoucherThymeController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/register")
    public String getVoucherPage() {
        return "voucher/register";
    }

    @PostMapping("/register")
    public String createVoucher(@ModelAttribute CreateVoucherRequest request, Model model) {
        voucherService.create(request);
        model.addAttribute("voucherType", request.getVoucherType().getVoucherTypeName());
        model.addAttribute("benefit", request.getBenefitValue());

        return "voucher/registerationComplete";
    }

    @GetMapping("/vouchers")
    public String getAllVouchers(Model model) {
        List<VoucherResponse> vouchers = voucherService.findAll();
        model.addAttribute("vouchers", vouchers);
        return "voucher/list";
    }

    @GetMapping("/{id}")
    public String getVoucherDetails(@PathVariable UUID id, Model model) {
        VoucherResponse voucher = voucherService.findById(id);
        model.addAttribute("voucher", voucher);
        return "voucher/details";
    }

    @GetMapping("/update/{id}")
    public String getUpdateVoucherPage(@PathVariable UUID id, Model model) {
        VoucherResponse voucher = voucherService.findById(id);
        model.addAttribute("id", id);
        return "voucher/update";
    }

    @PutMapping("/update/{id}")
    public String updateVoucher(
        @PathVariable UUID id, @ModelAttribute UpdateVoucherRequest request, Model model
    ) {
        voucherService.update(id, request);
        model.addAttribute("voucherType", request.getVoucherType().getVoucherTypeName());
        model.addAttribute("benefit", request.getBenefitValue());
        return "voucher/updateComplete";
    }

    @DeleteMapping("/delete/{id}")
    public String getDeleteVoucher(@PathVariable UUID id) {
        voucherService.deleteById(id);
        return "voucher/deleteComplete";
    }

}
