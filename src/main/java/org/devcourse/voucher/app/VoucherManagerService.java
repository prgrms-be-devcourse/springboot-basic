package org.devcourse.voucher.app;

import org.devcourse.voucher.domain.voucher.Voucher;
import org.devcourse.voucher.repository.VoucherRepository;

import java.util.List;

public class VoucherManagerService implements VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherManagerService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Voucher save(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
