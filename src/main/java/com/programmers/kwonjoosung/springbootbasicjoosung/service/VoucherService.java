package com.programmers.kwonjoosung.springbootbasicjoosung.service;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.FixedAmountDiscountVoucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.PercentDiscountVoucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> getVoucherList() {
        return voucherRepository.findAll();
    }

    public Voucher createAndSave(VoucherType voucherType, long discount) {
        return saveVoucher(createVoucher(voucherType, discount));
    }

    private Voucher createVoucher(VoucherType voucherType, long discount) {
        return switch (voucherType) {
            case FIXED -> new FixedAmountDiscountVoucher(discount);
            case PERCENT -> new PercentDiscountVoucher(discount);
        };
    }

    private Voucher saveVoucher(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }

}
