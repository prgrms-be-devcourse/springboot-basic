package com.dev.bootbasic.voucher.service;


import com.dev.bootbasic.voucher.domain.Voucher;
import com.dev.bootbasic.voucher.domain.VoucherFactory;
import com.dev.bootbasic.voucher.dto.VoucherCreateRequest;
import com.dev.bootbasic.voucher.dto.VoucherDetailsResponse;
import com.dev.bootbasic.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
@Transactional(readOnly = true)
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public UUID createVoucher(VoucherCreateRequest request) {
        Voucher createdVoucher = VoucherFactory.create(randomUUID(), request.voucherType(), request.discountAmount());
        return voucherRepository.saveVoucher(createdVoucher);
    }

    public VoucherDetailsResponse findVoucher(UUID voucherId) {
        Voucher foundVoucher = voucherRepository.findVoucher(voucherId);

        return VoucherDetailsResponse.from(foundVoucher);
    }

    public List<VoucherDetailsResponse> findAllVouchers() {
        Collection<Voucher> allVouchers = voucherRepository.getAllVouchers();

        return allVouchers.stream().map(VoucherDetailsResponse::from)
                .toList();
    }
}
