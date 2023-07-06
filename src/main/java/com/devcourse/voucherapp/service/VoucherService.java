package com.devcourse.voucherapp.service;

import com.devcourse.voucherapp.entity.VoucherType;
import com.devcourse.voucherapp.entity.dto.VoucherCreateRequestDto;
import com.devcourse.voucherapp.entity.voucher.Voucher;
import com.devcourse.voucherapp.repository.VoucherRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public Voucher create(VoucherCreateRequestDto request) {
        VoucherType voucherType = request.getType();
        Voucher voucher = voucherType.makeVoucher(UUID.randomUUID(), request.getDiscountAmount());

        return voucherRepository.save(voucher);
    }

    public List<Voucher> findAllVouchers() {
        return voucherRepository.findAllVouchers();
    }
}
