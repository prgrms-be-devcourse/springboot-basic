package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("memory")
public class MemoryVoucherService implements VoucherService {
    private final VoucherRepository voucherRepository;

    public MemoryVoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public List<Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher createVoucher(String type, long discountDegree) {
        return voucherRepository.insert(type, discountDegree);
    }

    @Override
    public void updateVoucher(Long voucherId, long discountDegree) {
        voucherRepository.update(voucherId, discountDegree);
    }

    @Override
    public Voucher findById(Long voucherId) {
        return voucherRepository.findById(voucherId);
    }

    @Override
    public void deleteAll() {
        voucherRepository.deleteAll();
    }
}
