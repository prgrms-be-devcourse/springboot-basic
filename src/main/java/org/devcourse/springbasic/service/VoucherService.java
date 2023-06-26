package org.devcourse.springbasic.service;

import org.devcourse.springbasic.controller.menu.RunByCreatesMenu;
import org.devcourse.springbasic.voucher.Voucher;
import org.devcourse.springbasic.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class VoucherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunByCreatesMenu.class);
    private final VoucherRepository voucherRepository;
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }


    public UUID save(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public Voucher findById(UUID voucherId) {
        Optional<Voucher> voucher = voucherRepository.findById(voucherId);
        Voucher foundVoucher = voucher.orElseThrow(() -> new IllegalArgumentException("해당 ID의 바우처가 존재하지 않습니다."));
        LOGGER.error("조회한 바우처 찾을 수 없음 => 조회 ID: {}", voucherId);
        return foundVoucher;
    }

}
