package org.prgrms.part1.engine.service;

import org.prgrms.part1.engine.domain.Voucher;
import org.prgrms.part1.engine.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public Voucher insertVoucher(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }
}
