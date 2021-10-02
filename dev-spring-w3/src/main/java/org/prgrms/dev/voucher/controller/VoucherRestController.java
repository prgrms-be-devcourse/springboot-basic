package org.prgrms.dev.voucher.controller;

import org.prgrms.dev.voucher.domain.Voucher;
import org.prgrms.dev.voucher.domain.dto.InsertVoucherDto;
import org.prgrms.dev.voucher.domain.dto.UpdateVoucherDto;
import org.prgrms.dev.voucher.service.VoucherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/voucher")
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public List<Voucher> voucherList() {
        return voucherService.listVoucher();
    }

    @GetMapping("/{voucherId}")
    public Voucher getVoucher(@PathVariable String voucherId) {
        return voucherService.getVoucher(UUID.fromString(voucherId));
    }

    @PostMapping
    public Voucher addVoucher(@RequestBody InsertVoucherDto insertVoucherDto) {
        return voucherService.createVoucher(insertVoucherDto);
    }

    @PutMapping
    public Voucher modifyVoucher(@RequestBody UpdateVoucherDto updateVoucherDto) {
        return voucherService.updateVoucherDiscount(updateVoucherDto);
    }

    @DeleteMapping("/{voucherId}")
    public void removeVoucher(@PathVariable String voucherId) {
        voucherService.deleteVoucher(UUID.fromString(voucherId));
    }

}
