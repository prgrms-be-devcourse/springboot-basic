package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.VoucherRepository;
import org.prgrms.kdt.strategy.Voucher;

import java.text.MessageFormat;
import java.util.UUID;

import static org.prgrms.kdt.exception.Message.CANNOT_FIND_VOUCHER;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format(CANNOT_FIND_VOUCHER + "{0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {
        //TODO
    }

}
