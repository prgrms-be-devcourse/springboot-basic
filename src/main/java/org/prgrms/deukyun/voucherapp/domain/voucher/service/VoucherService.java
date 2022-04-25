package org.prgrms.deukyun.voucherapp.domain.voucher.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.Voucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.VoucherRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

/**
 * 바우처 서비스
 */
@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public void insert(Voucher voucher) {
        voucherRepository.insert(voucher);
    }
}
