package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.voucher.Voucher;
import com.programmers.springbootbasic.domain.voucher.VoucherType;
import com.programmers.springbootbasic.repository.voucher.VoucherRepository;
import com.programmers.springbootbasic.util.IdProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final IdProvider<UUID> idProvider;

    public void create(VoucherType voucherType, Long amount) {
        Voucher voucher = voucherType.createVoucher(idProvider.generateId(), amount);
        voucherRepository.save(voucher);
    }

    public List<Voucher> getAll() {
        return voucherRepository.findAll();
    }
}
