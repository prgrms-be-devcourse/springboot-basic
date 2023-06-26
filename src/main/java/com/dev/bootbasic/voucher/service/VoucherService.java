package com.dev.bootbasic.voucher.service;


import com.dev.bootbasic.voucher.domain.Voucher;
import com.dev.bootbasic.voucher.domain.VoucherFactory;
import com.dev.bootbasic.voucher.dto.VoucherCreateRequest;
import com.dev.bootbasic.voucher.dto.VoucherDetailsResponse;
import com.dev.bootbasic.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toUnmodifiableList;

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
        Voucher createdVoucher = voucherFactory.create(request.voucherType(), request.discountAmount());
        return voucherRepository.saveVoucher(createdVoucher);
    }

    public VoucherDetailsResponse findVoucher(UUID voucherId) {
        Voucher foundVoucher = voucherRepository.findVoucher(voucherId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_VOUCHER_MESSAGE));

        return VoucherDetailsResponse.from(foundVoucher);
    }

    public List<VoucherDetailsResponse> findAllVouchers() {
        Collection<Voucher> allVouchers = voucherRepository.getAllVouchers();

        return allVouchers.stream().map(VoucherDetailsResponse::from)
                .collect(toUnmodifiableList());
    }

}
