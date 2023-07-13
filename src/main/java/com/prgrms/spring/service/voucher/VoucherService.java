package com.prgrms.spring.service.voucher;

import com.prgrms.spring.controller.dto.request.VoucherCreateRequestDto;
import com.prgrms.spring.domain.voucher.FixedAmountVoucher;
import com.prgrms.spring.domain.voucher.PercentDiscountVoucher;
import com.prgrms.spring.domain.voucher.Voucher;
import com.prgrms.spring.domain.voucher.VoucherType;
import com.prgrms.spring.repository.voucher.VoucherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    @Transactional
    public Voucher createVoucher(VoucherCreateRequestDto requestDto) {
        Voucher voucher = null;
        if (requestDto.getVoucherType() == VoucherType.FIXED_AMOUNT) {
            voucher = FixedAmountVoucher.newInstance(UUID.randomUUID(), requestDto.getDiscount());
        } else if (requestDto.getVoucherType() == VoucherType.PERCENT_DISCOUNT) {
            voucher = PercentDiscountVoucher.newInstance(UUID.randomUUID(), requestDto.getDiscount());
        }
        voucherRepository.insert(voucher);
        return voucher;
    }

    @Transactional(readOnly = true)
    public List<Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }
}
