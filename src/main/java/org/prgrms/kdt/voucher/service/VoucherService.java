package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
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

    public Voucher createVoucher(VoucherType voucherType) {
        Optional<Voucher> userVoucher = Optional.empty();
        if (voucherType == VoucherType.FIXED) {
            userVoucher = Optional.of(new FixedAmountVoucher(UUID.randomUUID()));
        }
        if (voucherType == VoucherType.PERCENT) {
            userVoucher = Optional.of(new PercentDiscountVoucher(UUID.randomUUID()));
        }

        Voucher voucher = userVoucher.get();
        voucherRepository.insert(voucher);
        return voucher;
    }

    public List<Voucher> findAll() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers;
    }
}
