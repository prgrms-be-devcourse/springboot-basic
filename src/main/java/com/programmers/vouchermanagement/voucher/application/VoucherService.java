package com.programmers.vouchermanagement.voucher.application;

import com.programmers.vouchermanagement.voucher.domain.DiscountPolicy;
import com.programmers.vouchermanagement.voucher.domain.DiscountType;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherRepository;
import com.programmers.vouchermanagement.voucher.dto.request.VoucherCreationRequest;
import com.programmers.vouchermanagement.voucher.dto.request.VoucherUpdateRequest;
import com.programmers.vouchermanagement.voucher.dto.response.VoucherResponse;
import com.programmers.vouchermanagement.voucher.exception.VoucherErrorCode;
import com.programmers.vouchermanagement.voucher.exception.VoucherException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherResponse createVoucher(VoucherCreationRequest request) {
        DiscountType discountType = request.type();
        int amount = request.amount();
        DiscountPolicy discountPolicy = discountType.createDiscountPolicy(amount);
        Voucher voucher = new Voucher(discountPolicy);
        Voucher savedVoucher = voucherRepository.save(voucher);
        return new VoucherResponse(savedVoucher);
    }

    public List<VoucherResponse> getVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream()
                .map(VoucherResponse::new)
                .collect(Collectors.toList());
    }

    public VoucherResponse getVoucher(UUID id) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new VoucherException(VoucherErrorCode.VOUCHER_NOT_FOUND));
        return new VoucherResponse(voucher);
    }

    public void updateVoucher(VoucherUpdateRequest request) {
        UUID id = request.id();
        existsVoucher(id);
        DiscountType discountType = request.type();
        int amount = request.amount();
        DiscountPolicy discountPolicy = discountType.createDiscountPolicy(amount);
        Voucher voucher = new Voucher(id, discountPolicy);
        voucherRepository.update(voucher);
    }

    public void deleteVoucher(UUID id) {
        existsVoucher(id);
        voucherRepository.deleteById(id);
    }

    private void existsVoucher(UUID id) {
        if (!voucherRepository.existById(id)) {
            throw new VoucherException(VoucherErrorCode.VOUCHER_NOT_FOUND);
        }
    }
}
