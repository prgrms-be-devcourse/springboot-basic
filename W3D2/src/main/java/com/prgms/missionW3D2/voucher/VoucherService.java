package com.prgms.missionW3D2.voucher;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherType voucherType , long figure) {
        return voucherType.createVoucher(voucherRepository, UUID.randomUUID(), figure);
    }

    public List<Voucher> getVoucherList() {
        return voucherRepository.voucherList();
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("can not find a voucher for {0}", voucherId)));
    }
}
