package org.prgrms.kdtspringvoucher.controller;


import org.prgrms.kdtspringvoucher.dto.VoucherDto;
import org.prgrms.kdtspringvoucher.entity.voucher.Voucher;
import org.prgrms.kdtspringvoucher.service.voucher.VoucherService;
import org.prgrms.kdtspringvoucher.entity.voucher.VoucherTypeNum;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public List<Voucher> getVouchers() {
        return voucherService.getAllVouchers();
    }

    @GetMapping("/{id}")
    public Voucher getVoucher(@PathVariable String id) {
        return voucherService.getVoucher(UUID.fromString(id)).get();
    }

    @GetMapping("/search")
    public List<Voucher> getVouchersByType(@RequestParam Optional<String> type) {
        return type
                .map(voucherService::getVouchersByType)
                .orElse(voucherService.getVouchersByPeriod());
    }

    @PostMapping
    public Voucher addVoucher(@RequestBody VoucherDto insertVoucherDto) {
        var voucher = VoucherTypeNum.getVoucherType(insertVoucherDto.voucherType(), UUID.randomUUID(), insertVoucherDto.amount());
        return voucherService.createVoucher(voucher).get();
    }

    @PutMapping
    public Voucher modifyVoucher(@RequestBody VoucherDto insertVoucherDto) {
        var voucher = VoucherTypeNum.getVoucherType(insertVoucherDto.voucherType(), insertVoucherDto.voucherId(), insertVoucherDto.amount());
        return voucherService.modifyVoucher(voucher).get();
    }

    @DeleteMapping("/{id}")
    public void removeVoucher(@PathVariable String id) {
        voucherService.removeVoucher(UUID.fromString(id));
    }

}
