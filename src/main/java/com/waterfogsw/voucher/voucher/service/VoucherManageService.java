package com.waterfogsw.voucher.voucher.service;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherManageService implements VoucherService {

    private VoucherRepository voucherRepository;

    @Override
    public Voucher saveVoucher(Voucher voucher) {
        validateVoucher(voucher);
        return voucherRepository.save(voucher);
    }

    private void validateVoucher(Voucher voucher) {
        switch (voucher.getType()) {
            case FIXED_AMOUNT -> {
                if(voucher.getValue() < 0)
                    throw new IllegalArgumentException();
            }
            case PERCENT_DISCOUNT -> {
                if(voucher.getValue() < 0 || voucher.getValue() > 100)
                    throw new IllegalArgumentException();
            }
        }
    }
}
