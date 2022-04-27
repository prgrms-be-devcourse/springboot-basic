package org.prgms.controller.api;

import org.prgms.domain.Voucher;
import org.prgms.service.VoucherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public List<Voucher> getAllVouchers() {
        return voucherService.getVouchers();
    }

    @GetMapping("/vouchers/{voucherType}")
    public List<Voucher> getVoucherByType(@PathVariable String voucherType) {
        return voucherService.getVoucherByType(voucherType);
    }

}
