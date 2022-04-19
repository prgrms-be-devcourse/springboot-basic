package org.programmers.kdt.weekly.voucher.service;

import java.util.List;
import java.util.UUID;
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

    public Voucher createVoucher(VoucherType voucherType, int value) {
        Voucher voucher = voucherType.create(UUID.randomUUID(), value);
        this.voucherRepository.insert(voucher);

        return voucher;
    }

    public List<Voucher> getVoucherList() {
        return this.voucherRepository.findAll();
    }

}