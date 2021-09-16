package org.prgms.voucher.api;

import org.prgms.voucher.CreateVoucherRequest;
import org.prgms.voucher.Voucher;
import org.prgms.voucher.VoucherService;
import org.prgms.voucher.VoucherType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1")
@RestController
public class ApiVoucherController {
    private final VoucherService voucherService;

    public ApiVoucherController(VoucherService voucherService) {
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

    @PostMapping("/voucher")
    public Voucher addFixedVoucher(@RequestBody CreateVoucherRequest createVoucherRequest) {
        var voucher = voucherService.createVoucher(createVoucherRequest.getVoucherType(), createVoucherRequest.getAmount());
        return voucher;
    }


}
