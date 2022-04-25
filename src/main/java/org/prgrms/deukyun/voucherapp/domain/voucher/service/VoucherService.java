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

    /**
     * 바우처 리포지토리에서 주어진 아이디의 바우처를 찾아 리턴
     *
     * @param voucherId 바우처 아이디
     * @return 바우처
     * @throws IllegalArgumentException 바우처 리포지토리에서 주어진 아이디의 바우처를 찾지 못한 경우 런 타임 예외
     */
    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public void insert(Voucher voucher) {
        voucherRepository.insert(voucher);
    }
}
