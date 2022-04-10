package com.pppp0722.vouchermanagement.voucher.service;

import com.pppp0722.vouchermanagement.voucher.model.FixedAmountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.PercentDiscountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createVoucher(String voucherType, UUID voucherId, long amount) {
        Voucher voucher = null;

        if(voucherType.equals("f")) {
            voucher = new FixedAmountVoucher(voucherId, amount);
        } else if(voucherType.equals("p")) {
            voucher = new PercentDiscountVoucher(voucherId, amount);
        }

        voucherRepository.insert(voucher);
    }

    public Optional<List<Voucher>> getVouchers(){
        List<Voucher> voucherList = voucherRepository.getVoucherList();

        if(voucherList.isEmpty()) return Optional.empty();

        return Optional.of(voucherRepository.getVoucherList());
    }
}
