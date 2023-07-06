package com.programmers.springweekly.service;

import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.domain.voucher.VoucherFactory;
import com.programmers.springweekly.domain.voucher.VoucherType;
import com.programmers.springweekly.repository.voucher.VoucherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public void saveVoucher(VoucherType voucherType, String discount) {
        Voucher voucher = VoucherFactory.createVoucher(UUID.randomUUID(), voucherType, discount);
        voucherRepository.save(voucher);
    }

    public List<Voucher> findVoucherAll() {
        return voucherRepository.findAll();
    }
}
