package org.prgms.kdtspringweek1.voucher.service;

import org.prgms.kdtspringweek1.voucher.entity.Voucher;
import org.prgms.kdtspringweek1.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void registerVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    public List<Voucher> searchAllVouchers() {
        return voucherRepository.findAllVouchers();
    }
}
