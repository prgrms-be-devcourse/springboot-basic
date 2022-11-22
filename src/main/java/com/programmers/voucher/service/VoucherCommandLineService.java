package com.programmers.voucher.service;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.repository.MemoryVoucherRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class VoucherCommandLineService {
    private final MemoryVoucherRepository memoryVoucherRepository;

    public VoucherCommandLineService(MemoryVoucherRepository memoryVoucherRepository) {
        this.memoryVoucherRepository = memoryVoucherRepository;
    }

    public void saveVoucher(Voucher voucher) {
        memoryVoucherRepository.insert(voucher);
    }

    public Map<UUID, Voucher> findAll() {
        return memoryVoucherRepository.findAll();
    }
}
