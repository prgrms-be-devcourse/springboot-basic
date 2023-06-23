package com.programmers.voucher.service;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.enumtype.VoucherType;
import com.programmers.voucher.repository.VoucherRepository;
import com.programmers.voucher.request.VoucherCreateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class VoucherService {
    private final static Logger log = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public UUID createVoucher(VoucherCreateRequest request) {
        VoucherType voucherType = request.getVoucherType();

        UUID voucherId = UUID.randomUUID();
        Voucher voucher = voucherType.createVoucher(voucherId, request);

        voucherRepository.save(voucher);

        log.info("새로운 바우처가 생성되었습니다. Voucher => {}", voucher.toString());
        return voucherId;
    }

    public List<Voucher> findVouchers() {
        return voucherRepository.findAll();
    }

}
