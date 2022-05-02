package org.prgms.kdtspringvoucher.voucher.controller;

import org.prgms.kdtspringvoucher.voucher.domain.Voucher;
import org.prgms.kdtspringvoucher.voucher.domain.VoucherType;
import org.prgms.kdtspringvoucher.voucher.dto.CreateVoucherRequest;
import org.prgms.kdtspringvoucher.voucher.dto.UpdateVoucherRequest;
import org.prgms.kdtspringvoucher.voucher.service.VoucherService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/vouchers")
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public List<Voucher> getVouchers(@RequestParam VoucherType voucherType, @RequestParam LocalDateTime from, @RequestParam LocalDateTime to) {
        return voucherService.getVouchers(voucherType, from, to);
    }

    @PostMapping
    public Voucher createVoucher(@RequestBody CreateVoucherRequest createVoucherRequest) {
        return voucherService.createVoucher(createVoucherRequest.getVoucherType(), createVoucherRequest.getAmount());
    }

    @GetMapping("{voucherId}")
    public Voucher getVouchersById(@PathVariable UUID voucherId) {
        return voucherService.getVoucherById(voucherId).orElse(null);
    }

    @PostMapping("{voucherId}")
    public Voucher updateVoucher(@PathVariable UUID voucherId, @RequestBody UpdateVoucherRequest updateVoucherRequest) {
        return voucherService.updateVoucherById(voucherId, updateVoucherRequest);
    }

    @DeleteMapping("{voucherId}")
    public void deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteVoucherById(voucherId);
    }
}
