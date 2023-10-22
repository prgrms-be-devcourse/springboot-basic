package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.voucher.VoucherFactory;
import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import com.programmers.vouchermanagement.repository.voucher.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public void createVoucher(VoucherType voucherType, Long amount) {
        Voucher voucher = VoucherFactory.create(voucherType, amount);
        voucherRepository.save(voucher);
    }

    public List<Voucher> getVouchers() {
        return voucherRepository.findAll();
    }
}
