package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.repository.FileVoucherRepository;
import org.prgrms.kdt.voucher.repository.MemoryVoucherRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherService {
    private final MemoryVoucherRepository memoryVoucherRepository;
    private final FileVoucherRepository fileVoucherRepository;

    public VoucherService(MemoryVoucherRepository memoryVoucherRepository, FileVoucherRepository fileVoucherRepository) {
        this.memoryVoucherRepository = memoryVoucherRepository;
        this.fileVoucherRepository = fileVoucherRepository;
    }

    public void createVoucher(String voucherType, int discountRate) {
        Voucher voucher = VoucherType.getVoucherType(voucherType, UUID.randomUUID(), discountRate);
        memoryVoucherRepository.insert(voucher);
        fileVoucherRepository.insert(voucher);
    }

    public MemoryVoucherRepository getVoucherRepository() {
        return memoryVoucherRepository;
    }
}
