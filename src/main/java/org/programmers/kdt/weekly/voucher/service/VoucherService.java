package org.programmers.kdt.weekly.voucher.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.programmers.kdt.weekly.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private static VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        VoucherService.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherType voucherType, int value) {
        Voucher voucher;
        try {
            voucher = voucherType.create(UUID.randomUUID(), value);
            voucherRepository.insert(voucher);
        } catch (IllegalArgumentException e) {
            throw e;
        }
        return voucher;
    }

    public Optional<List<Voucher>> voucherList() {
        return Optional.ofNullable(voucherRepository.findAll());
    }


}
