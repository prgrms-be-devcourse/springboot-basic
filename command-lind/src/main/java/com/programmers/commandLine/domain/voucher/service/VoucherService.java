package com.programmers.commandLine.domain.voucher.service;

import com.programmers.commandLine.domain.voucher.repository.VoucherRepository;
import com.programmers.commandLine.domain.voucher.repository.impl.VoucherRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }
    public UUID create() {
        voucherRepository.create();
    }
}
