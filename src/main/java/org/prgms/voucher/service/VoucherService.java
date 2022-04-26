package org.prgms.voucher.service;

import lombok.val;
import org.prgms.voucher.domain.Voucher;
import org.prgms.voucher.domain.VoucherRepository;
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

    public void createVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    public List<Voucher> listVoucher() {
        return voucherRepository.findAll();
    }

    public Optional<Voucher> getVoucher(UUID id) {
        return voucherRepository.findById(id);
    }

    public void deleteAllVouchers() {
        voucherRepository.deleteAll();
    }

    public long useVoucher(long beforeDiscount, UUID voucherId) {
        val voucher = voucherRepository.findById(voucherId);
        if (voucher.isEmpty())
            return beforeDiscount;

        return voucher.get().apply(beforeDiscount);
    }
}
