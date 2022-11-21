package com.programmers.kwonjoosung.springbootbasicjoosung.service;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherFactory;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Optional<Voucher> createVoucher(VoucherType voucherType, long discount) {
        Voucher voucher = VoucherFactory.createVoucher(voucherType, discount);
        boolean result = saveVoucher(voucher);
        if (result) return Optional.of(voucher);
        return Optional.empty();
    }

    private boolean saveVoucher(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }

    public Optional<Voucher> findVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public boolean updateVoucher(UUID voucherId, VoucherType voucherType, long discount) {
        if (findVoucher(voucherId).isPresent()) {
            Voucher newVoucher = VoucherFactory.createVoucher(voucherType, voucherId, discount);
            return voucherRepository.update(newVoucher);
        }
        return false;
    }

    public boolean deleteVoucher(UUID voucherId) {
        if (findVoucher(voucherId).isPresent()) {
            return voucherRepository.deleteById(voucherId);
        }
        return false;

    }


}
