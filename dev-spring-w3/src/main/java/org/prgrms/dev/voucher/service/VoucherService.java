package org.prgrms.dev.voucher.service;

import org.prgrms.dev.voucher.domain.Voucher;
import org.prgrms.dev.voucher.domain.VoucherType;
import org.prgrms.dev.voucher.repository.MemoryVoucherRepository;
import org.prgrms.dev.voucher.repository.VoucherRepository;

import java.util.List;
import java.util.UUID;

public class VoucherService {
    private VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find a voucher for " + voucherId));
    }

    public Voucher createVoucher(String type, Long value) {
        Voucher voucher = VoucherType.getVoucherType(type, value);
        voucherRepository.create(voucher);
        return voucher;
    }

    public List<Voucher> listVoucher() {
        return voucherRepository.findAll();
    }

    public void useVoucher(Voucher voucher) {

    }
}
