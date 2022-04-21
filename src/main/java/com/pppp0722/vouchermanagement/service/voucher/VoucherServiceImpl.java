package com.pppp0722.vouchermanagement.service.voucher;

import com.pppp0722.vouchermanagement.entity.voucher.FixedAmountVoucher;
import com.pppp0722.vouchermanagement.entity.voucher.PercentDiscountVoucher;
import com.pppp0722.vouchermanagement.entity.voucher.Voucher;
import com.pppp0722.vouchermanagement.entity.voucher.VoucherType;
import com.pppp0722.vouchermanagement.repository.voucher.VoucherRepository;
import com.pppp0722.vouchermanagement.service.voucher.VoucherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public void createVoucher(UUID voucherId, VoucherType voucherType, long amount, UUID memberId) {
        Voucher voucher = null;

        switch (voucherType) {
            case FIXED_AMOUNT:
                voucher = new FixedAmountVoucher(voucherId, amount, memberId);
                break;
            case PERCENT_DISCOUNT:
                voucher = new PercentDiscountVoucher(voucherId, amount, memberId);
                break;
            default:
                break;
        }

        voucherRepository.createVoucher(voucher);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherRepository.readVouchers();
    }

    @Override
    public List<Voucher> getVouchersByMemberId(UUID memberId) {
        return voucherRepository.readVouchersByMemberId(memberId);
    }
}
