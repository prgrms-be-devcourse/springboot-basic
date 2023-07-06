package com.programmers.voucher.service;

import com.programmers.voucher.controller.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.controller.voucher.dto.VoucherResponse;
import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public VoucherResponse createVoucher(VoucherCreateRequest request) {
        Voucher voucher = voucherRepository.insert(request.toEntity());
        return VoucherResponse.from(voucher);
    }

    public List<VoucherResponse> getAllVouchers() {
        return voucherRepository.findAll()
                .stream()
                .map(VoucherResponse::from)
                .toList();
    }

    public VoucherResponse getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .map(VoucherResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("존재하는 바우처가 없습니다."));
    }
}
