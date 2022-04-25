package org.prgrms.deukyun.voucherapp.domain.voucher.service;

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
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public void insert(Voucher voucher) {
        voucherRepository.insert(voucher);
    }
}
