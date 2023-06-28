package org.prgrms.kdtspringdemo.voucher.service;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.entity.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.PercentAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.prgrms.kdtspringdemo.voucher.ropository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherServiceImpl implements VoucherService {
    private static final String INVALID_VOUCHER_TYPE = "바우처 형식이 알맞지 않습니다.";
    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Voucher createVoucher(VoucherType voucherType, long discount) {
        switch (voucherType) {
            case FIXED -> {
                Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), voucherType, discount);
                return voucherRepository.save(fixedAmountVoucher);
            }
            case PERCENT -> {
                Voucher percentAmountVoucher = new PercentAmountVoucher(UUID.randomUUID(), voucherType, discount);
                return voucherRepository.save(percentAmountVoucher);
            }
            default -> {
                throw new IllegalArgumentException(INVALID_VOUCHER_TYPE);
            }
        }
    }

    @Override
    public List<Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }
}
