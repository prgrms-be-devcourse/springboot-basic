package org.prgms.springbootbasic.controller;

import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.domain.voucher.VoucherCreateDTO;
import org.prgms.springbootbasic.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public List<Voucher> voucherList() {
        return this.voucherService.findAll();
    }

    public Voucher createVoucher(VoucherCreateDTO voucherCreateDTO) {
        return this.voucherService.createVoucher(voucherCreateDTO);
    }

    public List<Voucher> findVouchers(UUID customerId) {
        return this.voucherService.findVouchers(customerId);
    }

    public Optional<Voucher> findOneVoucher(UUID voucherId) {
        return this.voucherService.findVoucher(voucherId);
    }

    public UUID deleteVouchers(UUID customerId) {
        return this.voucherService.deleteVouchers(customerId);
    }

    public Voucher allocateOneVoucher(Voucher voucher) {
        return this.voucherService.allocateVoucher(voucher);
    }
}
