package org.prgms.springbootbasic.controller;

import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.domain.voucher.VoucherCreateDTO;
import org.prgms.springbootbasic.service.VoucherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public List<Voucher> voucherList() {
        return this.voucherService.findAll();
    }

    @PostMapping("/vouchers/new")
    public Voucher createVoucher(VoucherCreateDTO voucherCreateDTO) {
        return this.voucherService.createVoucher(voucherCreateDTO);
    }

    @GetMapping("/vouchers/{customerId}")
    public List<Voucher> findVouchers(@PathVariable UUID customerId) {
        return this.voucherService.findVouchers(customerId);
    }

    @GetMapping("/vouchers/{voucherId}")
    public Optional<Voucher> findOneVoucher(@PathVariable UUID voucherId) {
        return this.voucherService.findVoucher(voucherId);
    }

    @DeleteMapping("/vouchers")
    public UUID deleteVouchers(UUID customerId) {
        return this.voucherService.deleteVouchers(customerId);
    }

    @PostMapping("/vouchers/{customerId}/{voucherId}")
    public UUID allocateOneVoucher(@PathVariable UUID customerId, @PathVariable UUID voucherId) {
        return this.voucherService.allocateVoucher(customerId, voucherId);
    }
}
