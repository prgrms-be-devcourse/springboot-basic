package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.repository.FileVoucherRepository;
import org.prgrms.kdt.voucher.repository.MemoryVoucherRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Profile("local")
public class MemoryVoucherService implements VoucherService{
    private final MemoryVoucherRepository memoryVoucherRepository;

    public MemoryVoucherService(MemoryVoucherRepository memoryVoucherRepository) {
        this.memoryVoucherRepository = memoryVoucherRepository;
    }

    @Override
    public void createVoucher(String voucherType, int discountRate) {
        Voucher voucher = VoucherType.getVoucherType(voucherType, UUID.randomUUID(), discountRate);
        memoryVoucherRepository.insert(voucher);
    }

    public MemoryVoucherRepository getVoucherRepository() {
        return memoryVoucherRepository;
    }
}
