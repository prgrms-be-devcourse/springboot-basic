package org.programmers.springbootbasic.service;

import lombok.RequiredArgsConstructor;
import org.programmers.springbootbasic.repository.VoucherRepository;
import org.programmers.springbootbasic.voucher.Voucher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    @Override
    public Voucher registerVoucher(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }

    @Override
    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId).orElseThrow(
                () -> new IllegalArgumentException("No voucher found")
        );
    }

    @Override
    public long applyVoucher(long beforeDiscount, Voucher voucher) {
        return voucher.discount(beforeDiscount);
    }

    @Override
    public void useVoucher(UUID voucherId) {
        voucherRepository.remove(voucherId);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }
}
