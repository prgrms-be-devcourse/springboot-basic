package org.prgrms.kdt.shop.service;

import org.prgrms.kdt.shop.domain.Voucher;
import org.prgrms.kdt.shop.repository.VoucherRepository;

import java.util.Optional;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Optional<Voucher> getVoucher(UUID voucherId) {
        return Optional.ofNullable(voucherRepository.findById(voucherId).orElseThrow(( ) -> new RuntimeException("Can not find a voucher for%s".formatted(voucherId))));
    }
}
