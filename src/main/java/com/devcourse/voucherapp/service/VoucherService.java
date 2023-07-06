package com.devcourse.voucherapp.service;

import com.devcourse.voucherapp.entity.VoucherType;
import com.devcourse.voucherapp.entity.dto.VoucherCreateRequestDto;
import com.devcourse.voucherapp.entity.dto.VoucherResponseDto;
import com.devcourse.voucherapp.entity.dto.VoucherUpdateRequestDto;
import com.devcourse.voucherapp.entity.dto.VouchersResponseDto;
import com.devcourse.voucherapp.entity.voucher.Voucher;
import com.devcourse.voucherapp.exception.NotFoundVoucherException;
import com.devcourse.voucherapp.repository.VoucherRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherResponseDto create(VoucherCreateRequestDto request) {
        VoucherType voucherType = request.getType();
        Voucher newVoucher = voucherType.makeVoucher(UUID.randomUUID(), request.getDiscountAmount());
        Voucher voucher = voucherRepository.save(newVoucher);

        return new VoucherResponseDto(voucher);
    }

    public VouchersResponseDto findAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAllVouchers();

        return new VouchersResponseDto(vouchers);
    }

    public VoucherResponseDto findVoucherById(String id) {
        Voucher voucher = voucherRepository.findVoucherById(id)
                .orElseThrow(() -> new NotFoundVoucherException(id));

        return new VoucherResponseDto(voucher);
    }

    public VoucherResponseDto update(VoucherUpdateRequestDto request) {
        VoucherType voucherType = request.getType();
        Voucher updatedVoucher = voucherType.makeVoucher(request.getId(), request.getDiscountAmount());
        Voucher voucher = voucherRepository.update(updatedVoucher);

        return new VoucherResponseDto(voucher);
    }
}
