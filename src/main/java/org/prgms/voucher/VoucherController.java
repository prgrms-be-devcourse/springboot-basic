package org.prgms.voucher;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1")
@RestController
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public List<Voucher> findVouchers() {
        return voucherService.getVouchers();
    }

    @GetMapping("/vouchers/{voucherId}")
    public Voucher findVoucher(@PathVariable("voucherId") UUID voucherId) {
        return voucherService.getVoucher(voucherId);
    }

    @DeleteMapping("/voucher/{voucherId}")
    public void deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        System.out.println("ssss");
        voucherService.deleteVoucher(voucherId);
    }

    @GetMapping("/vouchers/type/{voucherType}")
    public List<Voucher> findVouchersByType(@PathVariable("voucherType") VoucherType voucherType) {
        return voucherService.getVouchersByType(voucherType);
    }

    @PostMapping("/voucher/FIXED_AMOUNT")
    public Voucher addFixedVoucher(@RequestBody CreateVoucherRequest createVoucherRequest) {
        var voucher = voucherService.createVoucher(VoucherType.FIXED_AMOUNT, createVoucherRequest.getAmount());
        return voucher;
    }

    @PostMapping("/voucher/PERCENT_DISCOUNT")
    public Voucher addPercentVoucher(@RequestBody CreateVoucherRequest createVoucherRequest) {
        var voucher = voucherService.createVoucher(VoucherType.PERCENT_DISCOUNT, createVoucherRequest.getAmount());
        return voucher;
    }


}
