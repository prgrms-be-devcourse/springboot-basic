package org.programmers.kdt.weekly.voucher.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.programmers.kdt.weekly.voucher.VoucherDto;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.programmers.kdt.weekly.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(UUID voucherId, VoucherType voucherType, long value) {
        VoucherDto voucherDto = new VoucherDto(voucherId, value, LocalDateTime.now());

        return voucherRepository.insert(voucherType.create(voucherDto));
    }

    public List<Voucher> getVouchers() {
        return this.voucherRepository.findAll();
    }
}