package com.prgmrs.voucher.service;

import com.prgmrs.voucher.model.FixedAmountVoucher;
import com.prgmrs.voucher.model.PercentDiscountVoucher;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.repository.VoucherRepository;
import com.prgmrs.voucher.view.ConsoleViewEnum;
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

    public UUID createVoucher(long value, ConsoleViewEnum type) {
        Voucher voucher;
        UUID uuid = UUID.randomUUID();
        if("fixed".equals(type.name())) {
            voucher = new FixedAmountVoucher(uuid, value);
        } else if("percent".equals(type.name())) {
            voucher = new PercentDiscountVoucher(uuid, value);
        } else {
            throw new RuntimeException();
        }
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
