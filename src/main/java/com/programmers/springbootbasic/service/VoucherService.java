package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.voucher.Voucher;
import com.programmers.springbootbasic.domain.voucher.VoucherRepository;
import com.programmers.springbootbasic.service.dto.FixedAmountVoucherCreationRequest;
import com.programmers.springbootbasic.service.dto.PercentDiscountVoucherCreationRequest;
import com.programmers.springbootbasic.service.dto.VoucherResponses;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherType toVoucherType(String description) {
        return VoucherType.from(description);
    }

    public Voucher createFixedAmountVoucher(FixedAmountVoucherCreationRequest request) {
        Voucher fixedAmountVoucher = VoucherMapper.toFixedAmountVoucher(request);
        voucherRepository.save(fixedAmountVoucher);
        return fixedAmountVoucher;
    }

    public Voucher createPercentDiscountVoucher(PercentDiscountVoucherCreationRequest request) {
        Voucher percentDiscountVoucher = VoucherMapper.toPercentDiscountVoucher(request);
        voucherRepository.save(percentDiscountVoucher);
        return percentDiscountVoucher;
    }

    public VoucherResponses list() {
        return VoucherMapper.toVoucherResponseList(voucherRepository.findAll());
    }
}
