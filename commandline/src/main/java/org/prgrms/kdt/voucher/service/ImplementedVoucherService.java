package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImplementedVoucherService implements VoucherService {
    private final VoucherRepository voucherRepository;

    public ImplementedVoucherService(VoucherRepository voucherRepository) {
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
    public void updateVoucher(long voucherId, long discountDegree) {
        voucherRepository.update(voucherId, discountDegree);
    }

    @Override
    public Voucher findById(long voucherId) {
        return voucherRepository.findById(voucherId);
    }

    @Override
    public void deleteAll() {
        voucherRepository.deleteAll();
    }
}
