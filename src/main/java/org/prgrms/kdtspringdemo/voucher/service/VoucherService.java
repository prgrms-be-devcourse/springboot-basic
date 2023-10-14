package org.prgrms.kdtspringdemo.voucher.service;

import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherTypeFunction;
import org.prgrms.kdtspringdemo.voucher.repository.VoucherRepository;

import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherTypeFunction getVoucherType(String type) {
        return VoucherTypeFunction.findByCode(type);
    }

    public Voucher createVoucher(VoucherTypeFunction voucherType, UUID voucherId, long amount){
        Voucher voucher = voucherType.create(voucherId, amount);
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Map<UUID, Voucher> getVoucherList() {
        return voucherRepository.getAllVouchers().get();
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not fnd a voucher for {0}", voucherId)));
    }

    public void endVoucherService() {
        System.exit(0);
    }
}
