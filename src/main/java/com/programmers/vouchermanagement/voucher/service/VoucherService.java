package com.programmers.vouchermanagement.voucher.service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.programmers.vouchermanagement.util.Validator;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.UpdateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.repository.VoucherRepository;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherResponse create(CreateVoucherRequest createVoucherRequest) {
        Validator.validateDiscountValue(createVoucherRequest);
        Voucher voucher = new Voucher(UUID.randomUUID(), createVoucherRequest.discountValue(), createVoucherRequest.voucherType());
        voucherRepository.save(voucher);
        return VoucherResponse.from(voucher);
    }

    public List<VoucherResponse> readAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();

        if (vouchers.isEmpty()) {
            return Collections.emptyList();
        }

        return vouchers.stream()
                .map(VoucherResponse::from)
                .toList();
    }

    public VoucherResponse findById(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new NoSuchElementException("There is no voucher with %s".formatted(voucherId)));
        return VoucherResponse.from(voucher);
    }

    public VoucherResponse update(UpdateVoucherRequest updateVoucherRequest) {
        validateIdExisting(updateVoucherRequest.voucherId());
        Voucher voucher = new Voucher(updateVoucherRequest.voucherId(), updateVoucherRequest.discountValue(), updateVoucherRequest.voucherType());
        Voucher updatedVoucher = voucherRepository.save(voucher);
        return VoucherResponse.from(updatedVoucher);
    }

    public void deleteById(UUID voucherId) {
        validateIdExisting(voucherId);
        voucherRepository.deleteById(voucherId);
    }

    private void validateIdExisting(UUID voucherId) {
        if (!voucherRepository.existById(voucherId)) {
            throw new NoSuchElementException("There is no voucher with %s".formatted(voucherId));
        }
    }
}
