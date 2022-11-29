package org.prgrms.voucherapplication.domain.voucher.service;

import org.prgrms.voucherapplication.domain.voucher.entity.Voucher;
import org.prgrms.voucherapplication.domain.voucher.entity.VoucherType;
import org.prgrms.voucherapplication.domain.voucher.repository.VoucherRepository;
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

    public void create(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public Optional<Voucher> findById(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }

    public int deleteAll() {
        return voucherRepository.deleteAll();
    }

    public void deleteById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    public List<Voucher> findByType(VoucherType type) {
        return voucherRepository.findByType(type);
    }
}
