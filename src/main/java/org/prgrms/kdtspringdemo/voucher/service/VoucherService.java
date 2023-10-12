package org.prgrms.kdtspringdemo.voucher.service;

import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.repository.VoucherRepository;

import java.util.List;
import java.text.MessageFormat;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher() {
        System.out.println("create voucher");
        voucherRepository.insert(null);
        return null;
    }

    public List<Voucher> getVoucherList() {
        System.out.println("getvoucherlist");
        return null;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not fnd a voucher for {0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {
    }

    public void endVoucherService() {
        System.exit(0);
    }
}
