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
        if (voucher == null) {
            throw new RuntimeException("저장하고자 하는 바우처가 빈 값일 수 없습니다");
        }

        return voucherRepository.save(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
