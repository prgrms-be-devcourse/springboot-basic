package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public List<Voucher> getAllVouchers() {
        return voucherService.getAllVouchers();
    }

    @PostMapping("/create")
    public String createVoucher(@RequestParam("type") String voucherType,
                                @RequestParam(value = "amount", required = false) Integer amount,
                                @RequestParam(value = "percent", required = false) Integer percent) {
        if (voucherType.equals("fixed")) {
            if (amount != null) {
                voucherService.createFixedAmountVoucher(amount);
                return "Fixed amount voucher created.";
            } else {
                return "Amount parameter is missing for fixed voucher creation.";
            }
        } else if (voucherType.equals("percent")) {
            if (percent != null) {
                voucherService.createPercentDiscountVoucher(percent);
                return "Percent discount voucher created.";
            } else {
                return "Percent parameter is missing for percent voucher creation.";
            }
        } else {
            return "Unknown voucher type: " + voucherType;
        }
    }

    @DeleteMapping("/remove/{id}")
    public String removeVoucher(@PathVariable("id") String voucherId) {
        UUID id = UUID.fromString(voucherId);
        voucherService.removeVoucherById(id);
        return "Voucher removed.";
    }
}
