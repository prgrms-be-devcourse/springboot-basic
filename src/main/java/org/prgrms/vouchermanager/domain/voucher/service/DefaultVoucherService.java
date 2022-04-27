package org.prgrms.vouchermanager.domain.voucher.service;

import org.prgrms.vouchermanager.domain.voucher.domain.Voucher;
import org.prgrms.vouchermanager.domain.voucher.domain.VoucherRepository;
import org.prgrms.vouchermanager.domain.voucher.domain.VoucherType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultVoucherService implements VoucherService {

    private final VoucherRepository voucherRepository;

    public DefaultVoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public UUID createVoucher(String type, Long amount) {
        Voucher voucher = Voucher.create(UUID.randomUUID(), VoucherType.valueOf(type.toUpperCase()), amount);
        Voucher insertedVoucher = voucherRepository.insert(voucher);

        return insertedVoucher.getId();
    }

    @Override
    public List<Voucher> findVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher findVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new IllegalArgumentException("저장되지 않은 voucherId 입니다."));
    }
}
