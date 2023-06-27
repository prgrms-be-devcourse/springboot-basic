package com.demo.voucher.service;

import com.demo.voucher.domain.FixedAmountVoucher;
import com.demo.voucher.domain.PercentDiscountVoucher;
import com.demo.voucher.domain.Voucher;
import com.demo.voucher.domain.VoucherType;
import com.demo.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class VoucherServiceImpl implements VoucherService {
    private static final String NOT_FOUND_VOUCHER = "바우처를 찾을 수 없습니다.";
    private static final String VOUCHER_NOT_CREATED_ERROR = "바우처가 생성되지 않았습니다.";
    private final VoucherRepository voucherRepository;


    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(NOT_FOUND_VOUCHER));
    }

    @Override
    public Map<UUID, Voucher> findAllVouchers() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher createVoucher(VoucherType voucherType, String amount) {
        UUID uuid = UUID.randomUUID();
        int amountValue = Integer.parseInt(amount);
        switch (voucherType) {
            case FIXED_AMOUNT -> {
                return voucherRepository.insert(new FixedAmountVoucher(uuid, amountValue));
            }
            case PERCENT_DISCOUNT -> {
                return voucherRepository.insert(new PercentDiscountVoucher(uuid, amountValue));
            }
            default -> throw new RuntimeException(VOUCHER_NOT_CREATED_ERROR);
        }
    }
}
