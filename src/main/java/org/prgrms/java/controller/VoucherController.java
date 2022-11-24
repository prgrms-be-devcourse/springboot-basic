package org.prgrms.java.controller;

import org.prgrms.java.common.Mapper;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.service.voucher.VoucherService;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Voucher createVoucher(long amount, String type, LocalDateTime createdAt, LocalDateTime expiredAt) {
        Voucher voucher = Mapper.mapToVoucher(type, UUID.randomUUID(), null, amount, createdAt, expiredAt, false);
        return voucherService.saveVoucher(voucher);
    }

    public Voucher findVoucherById(UUID voucherId) {
        return voucherService.getVoucher(voucherId);
    }

    public List<Voucher> findVouchersByOwner(UUID customerId) {
        return voucherService.getVoucherByOwner(customerId);
    }

    public List<Voucher> findVouchers() {
        return voucherService.getAllVouchers();
    }

    public Voucher updateVoucher(UUID voucherId, UUID ownerId, LocalDateTime expiredAt, boolean used) {
        Voucher voucher = voucherService.getVoucher(voucherId);
        voucher.setOwnerId(ownerId);
        voucher.setExpiredAt(expiredAt);
        voucher.setUsed(used);

        return voucherService.updateVoucher(voucher);
    }

    public Voucher useVoucher(UUID voucherId) {
        Voucher voucher = voucherService.getVoucher(voucherId);
        return voucherService.useVoucher(voucher);
    }

    public Voucher allocateVoucher(UUID voucherId, UUID customerId) {
        Voucher voucher = voucherService.getVoucher(voucherId);
        return voucherService.allocateVoucher(voucher, customerId);
    }

    public Voucher detachOwnerFromVoucher(UUID voucherId) {
        Voucher voucher = voucherService.getVoucher(voucherId);
        return voucherService.detachOwnerFromVoucher(voucher);
    }

    public void deleteVoucher(UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
    }

    public long deleteVouchers() {
        return voucherService.deleteAllVouchers();
    }
}
