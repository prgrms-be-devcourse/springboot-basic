package com.waterfogsw.voucher.voucher.service;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherManageService implements VoucherService {

    private VoucherRepository voucherRepository;

    @Override
    public Voucher saveVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }
}
