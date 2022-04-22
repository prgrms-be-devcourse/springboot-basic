package com.pppp0722.vouchermanagement.voucher.service;

import com.pppp0722.vouchermanagement.voucher.model.FixedAmountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.PercentDiscountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.model.VoucherType;
import com.pppp0722.vouchermanagement.voucher.repository.VoucherRepository;
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

    public void createVoucher(VoucherType voucherType, UUID voucherId, long amount) {
        Voucher voucher = null;

        switch (voucherType) {
            case FIXED_AMOUNT:
                voucher = new FixedAmountVoucher(voucherId, amount);
                break;
            case PERCENT_DISCOUNT:
                voucher = new PercentDiscountVoucher(voucherId, amount);
                break;
            default:
                break;
        }

        voucherRepository.insert(voucher);
    }

    public Optional<List<Voucher>> getOptionalVoucherList(){
        List<Voucher> voucherList = voucherRepository.getVoucherList();

        if(voucherList.isEmpty()) return Optional.empty();

        return Optional.of(voucherRepository.getVoucherList());
    }
}
