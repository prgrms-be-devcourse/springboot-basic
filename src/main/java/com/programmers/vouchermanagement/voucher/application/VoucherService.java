package com.programmers.vouchermanagement.voucher.application;

import com.programmers.vouchermanagement.voucher.domain.FixedAmountVoucher;
import com.programmers.vouchermanagement.voucher.domain.PercentDiscountVoucher;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherRepository;
import com.programmers.vouchermanagement.voucher.dto.VoucherDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createVoucher(VoucherDto.RequestDto request) {
        String discountType = request.discountType();
        int discountAmount = request.discountAmount();
        Voucher voucher = matchVoucher(discountType, discountAmount);
        voucherRepository.save(voucher);
    }

    private static Voucher matchVoucher(String discountType, int discountAmount) {
        if (discountType.equals("fix")) {
            return new FixedAmountVoucher(UUID.randomUUID(), discountAmount);
        }
        if (discountType.equals("percent")) {
            return new PercentDiscountVoucher(UUID.randomUUID(), discountAmount);
        }
        throw new IllegalArgumentException("생성할 수 없는 바우처입니다. 다시 선택해주세요.");
    }
}
