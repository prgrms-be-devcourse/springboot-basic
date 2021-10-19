package org.prgrms.dev.voucher.controller;

import org.prgrms.dev.voucher.domain.Voucher;
import org.prgrms.dev.voucher.domain.dto.InsertVoucherDto;
import org.prgrms.dev.voucher.domain.dto.UpdateVoucherDto;
import org.prgrms.dev.voucher.service.VoucherService;
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
        return voucherService.getVouchers();
    }

    @GetMapping("/search")
    public List<Voucher> getVouchersByType(@RequestParam Optional<String> type) {
        return type
            .map(voucherService::getVouchersByType)
            .orElse(voucherService.getVouchersByPeriod());
    }

    @GetMapping("/{id}")
    public Voucher getVoucher(@PathVariable String id) {
        return voucherService.getVoucher(UUID.fromString(id));
    }

    @PostMapping
    public Voucher addVoucher(@RequestBody InsertVoucherDto insertVoucherDto) {
        return voucherService.addVoucher(insertVoucherDto);
    }

    @PutMapping
    public Voucher modifyVoucher(@RequestBody UpdateVoucherDto updateVoucherDto) {
        return voucherService.modifyVoucher(updateVoucherDto);
    }

    @DeleteMapping("/{id}")
    public void removeVoucher(@PathVariable String id) {
        voucherService.removeVoucher(UUID.fromString(id));
    }

}
