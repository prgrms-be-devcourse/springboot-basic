package com.dev.bootbasic.voucher.service;


import com.dev.bootbasic.voucher.domain.*;
import com.dev.bootbasic.voucher.dto.VoucherCreateRequest;
import com.dev.bootbasic.voucher.dto.VoucherDetailsResponse;
import com.dev.bootbasic.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private static final String NOT_FOUND_VOUCHER_MESSAGE = "찾을 수 없는 바우처입니다.";
    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;

    public VoucherService(VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
        this.voucherRepository = voucherRepository;
        this.voucherFactory = voucherFactory;
    }

    public UUID createVoucher(VoucherCreateRequest request) {
        Voucher createdVoucher = voucherFactory.createVoucher(request);
        return voucherRepository.saveVoucher(createdVoucher);
    }

    public VoucherDetailsResponse findVoucher(UUID voucherId) {
        Optional<Voucher> voucher = voucherRepository.findVoucher(voucherId);
        Voucher foundVoucher = voucher.orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_VOUCHER_MESSAGE));

        VoucherType voucherType = foundVoucher.getVoucherType();
        int discountAmount = foundVoucher.getDiscountAmount();
        return new VoucherDetailsResponse(voucherType, discountAmount);
    }

}
