package org.prgrms.kdtspringhomework.voucher.service;

import org.prgrms.kdtspringhomework.voucher.domain.Voucher;
import org.prgrms.kdtspringhomework.voucher.repository.VoucherRepository;

import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(String.format("Can not find a voucher for %s", voucherId)));
    }

    public void addVoucher(Voucher voucher) {
        voucherRepository.add(voucher);
    }

    public void printVouchers() {
        for (Voucher voucher : voucherRepository.findAll()) {
            System.out.println(voucher.toString());
        }
    }

    public void useVoucher(Voucher voucher) {
        //강의사용 메소드
    }
}
