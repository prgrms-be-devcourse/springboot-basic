package org.prgms.voucher;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String vouchersPage(Model model) {
        var vouchers = voucherService.getVouchers();
        System.out.println(vouchers);
        model.addAttribute("vouchers", vouchers);
        return "voucher-list";
    }

    @GetMapping("new-voucher")
    public String newVoucherPage() {
        return "new-voucher";
    }

    @PostMapping("/vouchers")
    public String newProduct(CreateVoucherRequest createVoucherRequest) {
        voucherService.createVoucher(
                createVoucherRequest.getVoucherType(),
                createVoucherRequest.getAmount());
        return "redirect:/vouchers";
    }

}
