package org.prgrms.kdt.controller.view;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherViewController {

    private final VoucherService voucherService;

    public VoucherViewController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("vouchers/customers/{customerId}")
    public String findVoucherByCustomerId(@PathVariable("customerId") UUID customerId, Model model) {
        List<Voucher> VoucherList = voucherService.getVouchersByCustomerId(customerId);
        if(VoucherList.isEmpty()) {
            return "views/404";
        }

        model.addAttribute("vouchers", VoucherList);
        return "views/customer-detail";
    }

    @PostMapping("vouchers/{voucherId}/customers/{customerId}")
    public String deleteVoucherByCustomerId(@PathVariable("voucherId") UUID voucherId, @PathVariable("customerId") UUID customerId) {
        voucherService.deleteVoucher(voucherId, customerId);
        return "redirect:/customers";
    }
}
