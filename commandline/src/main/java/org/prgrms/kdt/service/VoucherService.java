package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.util.VoucherType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }

    public Voucher createVoucher(String type, UUID voucherId, long discountDegree) {
        Voucher voucher = VoucherType.createVoucher(type, voucherId, discountDegree);
        return voucherRepository.insert(voucher);
    }
}
