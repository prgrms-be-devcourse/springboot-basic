package com.programmers.voucher.service;

import com.programmers.voucher.controller.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.controller.voucher.dto.VoucherCreateResponse;
import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherCreateResponse createVoucher(VoucherCreateRequest request) {
        Voucher voucher = switch (request.voucherType()) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(UUID.randomUUID(), request.discountAmount());
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(UUID.randomUUID(), request.discountAmount());
        };
        return VoucherCreateResponse.from(voucherRepository.insert(voucher));
    }

    public List<Voucher> getVoucherList() {
        return voucherRepository.findAll();
    }
}
