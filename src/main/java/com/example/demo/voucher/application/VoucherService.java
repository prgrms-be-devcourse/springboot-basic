package com.example.demo.voucher.application;

import com.example.demo.voucher.domain.Voucher;
import com.example.demo.voucher.domain.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public void createVoucher(String voucherType, long value) {
        UUID voucherId = UUID.randomUUID();
        VoucherType type = VoucherType.valueOf(voucherType);
        voucherRepository.insert(type.createVoucher(voucherId, value));
    }

    public List<Voucher> listVouchers() {
        return voucherRepository.findAll();
    }

    public void deleteAll() {
        voucherRepository.deleteAll();
    }

}
