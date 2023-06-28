package com.prgmrs.voucher.service;

import com.prgmrs.voucher.model.FixedAmountVoucher;
import com.prgmrs.voucher.model.PercentDiscountVoucher;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.repository.VoucherRepository;
import com.prgmrs.voucher.repository.VoucherRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {
    private VoucherRepository voucherRepository;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public UUID createFixedAmountVoucher(long value) {
        UUID uuid = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(uuid, value);
        voucherRepository.save(voucher);
        return uuid;

    }

    public UUID createPercentDiscountVoucher(long value) {
        UUID uuid = UUID.randomUUID();
        Voucher voucher = new PercentDiscountVoucher(uuid, value);
        voucherRepository.save(voucher);
        return uuid;
    }

    public Map<UUID, Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public Voucher findVoucherById(UUID uuid) {
        return voucherRepository.findVoucherById(uuid);
    }
}
