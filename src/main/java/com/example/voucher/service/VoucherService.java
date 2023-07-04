package com.example.voucher.service;

import com.example.voucher.domain.Voucher;
import com.example.voucher.repository.VoucherRepository;
import java.text.MessageFormat;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new NoSuchElementException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }
}
