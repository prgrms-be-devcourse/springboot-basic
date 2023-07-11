package com.devcourse.voucherapp.service;

import com.devcourse.voucherapp.entity.voucher.VoucherType;
import com.devcourse.voucherapp.entity.voucher.request.VoucherCreateRequestDto;
import com.devcourse.voucherapp.entity.voucher.response.VoucherResponseDto;
import com.devcourse.voucherapp.entity.voucher.request.VoucherUpdateRequestDto;
import com.devcourse.voucherapp.entity.voucher.response.VouchersResponseDto;
import com.devcourse.voucherapp.entity.voucher.Voucher;
import com.devcourse.voucherapp.utils.exception.voucher.NotFoundVoucherException;
import com.devcourse.voucherapp.repository.voucher.VoucherRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private static final int ZERO = 0;

    private final VoucherRepository voucherRepository;

    public VoucherResponseDto create(VoucherCreateRequestDto request) {
        VoucherType voucherType = request.getType();
        Voucher newVoucher = voucherType.makeVoucher(UUID.randomUUID(), request.getDiscountAmount());
        Voucher voucher = voucherRepository.save(newVoucher);

        return VoucherResponseDto.from(voucher);
    }

    public VouchersResponseDto findAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAllVouchers();

        return VouchersResponseDto.from(vouchers);
    }

    public VoucherResponseDto findVoucherById(String id) {
        Voucher voucher = voucherRepository.findVoucherById(id)
                .orElseThrow(() -> new NotFoundVoucherException(id));

        return VoucherResponseDto.from(voucher);
    }

    public VoucherResponseDto update(VoucherUpdateRequestDto request) {
        VoucherType voucherType = request.getType();
        Voucher updatedVoucher = voucherType.makeVoucher(request.getId(), request.getDiscountAmount());
        Voucher voucher = voucherRepository.update(updatedVoucher);

        return VoucherResponseDto.from(voucher);
    }

    public void deleteById(String id) {
        int deletionCounts = voucherRepository.deleteById(id);

        if (deletionCounts == ZERO) {
            throw new NotFoundVoucherException(id);
        }
    }
}
