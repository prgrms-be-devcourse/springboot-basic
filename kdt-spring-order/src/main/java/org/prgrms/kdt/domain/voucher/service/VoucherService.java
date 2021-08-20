package org.prgrms.kdt.domain.voucher.service;

import org.prgrms.kdt.domain.voucher.domain.Voucher;
import org.prgrms.kdt.domain.voucher.repository.VoucherRepository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find voucher for" + voucherId));
    }

    public void useVoucher(Voucher voucher) {
    }

    public boolean saveVoucher(Voucher voucher) {
        return voucherRepository.save(voucher); // 바우처가 정상적으로 추가되었을 경우 true 반환
    }

    public Map<UUID, Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }
}
