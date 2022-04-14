package com.prgrms.vouchermanagement.voucher.service;

import com.prgrms.vouchermanagement.voucher.FixedAmountVoucher;
import com.prgrms.vouchermanagement.voucher.PercentDiscountVoucher;
import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository repository;

    public VoucherService(VoucherRepository repository) {
        this.repository = repository;
    }

    public void addVoucher(VoucherType voucherType, long amount) throws IllegalArgumentException {
        Voucher newVoucher = createVoucher(voucherType, amount);
        repository.save(newVoucher);
    }

    private Voucher createVoucher(VoucherType voucherType, long amount) throws IllegalArgumentException {
        switch (voucherType) {
            case FIXED_DISCOUNT:
                return FixedAmountVoucher.of(amount);
            case PERCENT_DISCOUNT:
                return PercentDiscountVoucher.of(amount);
            default:
                throw new IllegalArgumentException("일치하는 VoucherType이 없습니다");
        }
    }

    public List<Voucher> findAllVouchers() {
        return repository.findAll();
    }

}
