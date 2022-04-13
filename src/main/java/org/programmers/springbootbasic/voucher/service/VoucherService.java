package org.programmers.springbootbasic.voucher.service;

import org.programmers.springbootbasic.voucher.model.FixedAmountVoucher;
import org.programmers.springbootbasic.voucher.model.PercentDiscountVoucher;
import org.programmers.springbootbasic.voucher.model.Voucher;
import org.programmers.springbootbasic.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    static final String FixedAmountVoucher = "1";
    static final String PercentDiscountVoucher = "2";

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException
                        (MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public Voucher saveVoucher(Voucher voucher) {
        voucherRepository.insert(voucher);
        return voucher;
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public Voucher createVoucher(long amount, String type) {
        switch (type) {
            case FixedAmountVoucher -> {
                return new FixedAmountVoucher(UUID.randomUUID(), amount);
            }
            case PercentDiscountVoucher -> {
                return new PercentDiscountVoucher(UUID.randomUUID(), amount);
            }
            default -> {
                return null;
            }
        }
    }
}
