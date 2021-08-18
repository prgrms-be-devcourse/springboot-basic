package org.prgrms.kdtbespring.service;

import org.prgrms.kdtbespring.entity.Voucher;
import org.prgrms.kdtbespring.repository.VoucherRepository;

import java.text.MessageFormat;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a Voucher for {0}", voucherId)));
    }

    // 바우처 사용했을 때
    public void useVoucher(Voucher voucher) {
    }
}
