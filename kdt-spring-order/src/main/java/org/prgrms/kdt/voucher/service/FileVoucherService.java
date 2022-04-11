package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.repository.FileVoucherRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Profile("dev")
public class FileVoucherService implements VoucherService{
    private final FileVoucherRepository fileVoucherRepository;

    public FileVoucherService(FileVoucherRepository fileVoucherRepository) {
        this.fileVoucherRepository = fileVoucherRepository;
    }

    @Override
    public void createVoucher(String voucherType, int discountRate) {
        Voucher voucher = VoucherType.getVoucherType(voucherType, UUID.randomUUID(), discountRate);
        fileVoucherRepository.insert(voucher);
    }
}
