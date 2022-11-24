package org.prgrms.kdtspringdemo.domain.voucher;

import org.prgrms.kdtspringdemo.domain.voucher.model.CreateVoucherRequest;
import org.prgrms.kdtspringdemo.domain.voucher.model.VoucherType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/")
    public String StartPage() {
        return "redirect:/vouchers";
    }

    @GetMapping("/vouchers")
    public String VouchersPage(Model model) {
        var vouchers = voucherService.getAllVoucherList();
        model.addAttribute("vouchers", vouchers);
        return "vouchers";
    }

    @GetMapping("/vouchers/{voucherId}")
    public String VoucherDetailPage(@PathVariable("voucherId") String userName, Model model) {
        var voucher = voucherService.getVoucher(UUID.fromString(userName));
        model.addAttribute("voucher", voucher);
        return "voucher-detail";
    }

    @GetMapping("/vouchers/new")
    public String createVoucherPage(Model model) {
        var voucherTypes = VoucherType.values();
        model.addAttribute("voucherTypes", voucherTypes);
        return "voucher-new";
    }

    @PostMapping("/vouchers/new")
    public String CreateVoucher(Model model, CreateVoucherRequest createVoucherRequest) {
        try {
            voucherService.createVoucher(VoucherType.getTypeByName(createVoucherRequest.voucherType()),
                    createVoucherRequest.amountOrPercent());
        } catch (Exception e) {
            e.getStackTrace();
            model.addAttribute("createVoucherFail", true);
            return createVoucherPage(model);
        }

        return "redirect:/vouchers";
    }
//    @PostMapping("/voucher/new")
//    public String createVoucher(){
//
//    }
}
