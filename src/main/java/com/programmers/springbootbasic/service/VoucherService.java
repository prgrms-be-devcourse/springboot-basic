package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.voucher.Voucher;
import com.programmers.springbootbasic.domain.voucher.VoucherType;
import com.programmers.springbootbasic.repository.voucher.VoucherRepository;
import com.programmers.springbootbasic.util.UuidProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final UuidProvider uuidProvider;

    public void create(VoucherType voucherType, Long amount) {
        Voucher voucher = voucherType.createVoucher(uuidProvider.generateUUID(), amount);
        voucherRepository.save(voucher);
    }

    public List<Voucher> getAll() {
        return voucherRepository.findAll();
    }
}
