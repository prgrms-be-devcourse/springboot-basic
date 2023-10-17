package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.voucher.VoucherFactory;
import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import com.programmers.vouchermanagement.repository.voucher.VoucherRepository;
import com.programmers.vouchermanagement.util.IdProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final IdProvider<UUID> idProvider;

    public void createVoucher(VoucherType voucherType, Long amount) {
        Voucher voucher = VoucherFactory.create(voucherType, idProvider.generateId(), amount);
        voucherRepository.save(voucher);
    }

    public List<Voucher> getVouchers() {
        return voucherRepository.findAll();
    }
}
