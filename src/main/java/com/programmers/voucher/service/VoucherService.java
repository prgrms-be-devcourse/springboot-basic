package com.programmers.voucher.service;

import com.programmers.voucher.controller.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.controller.voucher.dto.VoucherResponse;
import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.repository.voucher.VoucherRepository;
import com.programmers.voucher.view.dto.DiscountAmount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
        Voucher voucher = checkExisted(voucherRepository.findById(voucherId));
        return VoucherResponse.from(voucher);
    }

    @Transactional
    public VoucherResponse updateVoucher(UUID voucherId, DiscountAmount discountAmount) {
        Voucher voucher = checkExisted(voucherRepository.findById(voucherId));
        voucher.update(discountAmount.getAmount());
        return VoucherResponse.from(voucherRepository.update(voucher));
    }

    private Voucher checkExisted(Optional<Voucher> voucher) {
        return voucher.orElseThrow(() -> new IllegalArgumentException("존재하는 바우처가 없습니다."));
    }
}
