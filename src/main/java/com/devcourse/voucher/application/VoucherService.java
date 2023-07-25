package com.devcourse.voucher.application;

import com.devcourse.voucher.domain.Voucher;
import com.devcourse.voucher.domain.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void create(int discount, LocalDateTime expiredAt, Voucher.Type type) {
        Voucher voucher = new Voucher(discount, expiredAt, type);
        voucherRepository.save(voucher);
    }

    public List<String> findAll() {
        return voucherRepository.findAll().stream()
                .map(Voucher::toText)
                .toList();
    }
}
