package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.VoucherMachine;
import org.prgrms.kdt.domain.VoucherRepository;
import org.prgrms.kdt.domain.VoucherType;
import org.prgrms.kdt.strategy.Voucher;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import static org.prgrms.kdt.exception.Message.CANNOT_FIND_VOUCHER;

public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final VoucherMachine voucherMachine;

    public VoucherService(VoucherRepository voucherRepository, VoucherMachine voucherMachine) {
        this.voucherRepository = voucherRepository;
        this.voucherMachine = voucherMachine;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format(CANNOT_FIND_VOUCHER + "{0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {
        //TODO
    }

    public Voucher create(String voucherType) {
        return voucherMachine.createVoucher(VoucherType.valueOf(voucherType));
    }

}
