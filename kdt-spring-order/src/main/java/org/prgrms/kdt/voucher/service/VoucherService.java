package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createVoucher(String voucherType, int discountRate) {
        Voucher voucher = VoucherType.getVoucherType(voucherType, UUID.randomUUID(), discountRate);
        voucherRepository.insert(voucher);
    }

}
