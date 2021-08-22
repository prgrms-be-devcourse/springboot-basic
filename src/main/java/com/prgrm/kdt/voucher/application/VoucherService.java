package com.prgrm.kdt.voucher.application;

import com.prgrm.kdt.voucher.domain.Voucher;
import com.prgrm.kdt.voucher.repository.VoucherRepository;

import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {
    }

    public Map<UUID, Voucher> findAllVoucher() {
        return voucherRepository.findAll();
    }
}
